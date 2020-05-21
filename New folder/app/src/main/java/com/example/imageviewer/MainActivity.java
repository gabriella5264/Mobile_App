package com.example.imageviewer;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.imageviewer.model.PhotosResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import network.PhotoRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    private ImageListAdapter adapter;
    private PhotoRepository repository;
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private HomeFragment homeFragment;
    private NotifFragment notifFragment;
    private AccountFragment accountFragment;

    SwipeRefreshLayout swipeRefreshLayout;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        homeFragment = new HomeFragment();
        notifFragment = new NotifFragment();
        accountFragment = new AccountFragment();

        repository = PhotoRepository.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.photoListView);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);


        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        setFragment(homeFragment);


        adapter = new ImageListAdapter(this);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        repository = PhotoRepository.getInstance();
        Call<PhotosResponse> call = repository.getInterestingPhotos(1);

        call.enqueue(photosCallback);


        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home :
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(homeFragment);
                        return true;

                    case R.id.nav_notif:
                        mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(notifFragment);
                        return true;

                    case R.id.nav_account:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimaryDark);
                        setFragment(accountFragment);
                        return true;

                    default:
                        return false;

                }
            }
        });

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();

                adapter.notifyDataSetChanged();

                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void refresh(){
        Refresh.refreshApp(this);
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.main_frame, fragment);

        fragmentTransaction.commit();

    }


    private Callback<PhotosResponse> photosCallback = new Callback<PhotosResponse>() {
        @Override
        public void onResponse(Call<PhotosResponse> call, Response<PhotosResponse> response) {
            PhotosResponse resp = response.body();

            if (resp != null) {
                progressBar.setVisibility(View.GONE);
                repository.photoList.addAll(resp.getPhotoList());
                adapter.notifyDataSetChanged();
            } else {

            }
        }

        @Override
        public void onFailure(Call<PhotosResponse> call, Throwable t) {
            ExampleDialog exampleDialog = new ExampleDialog();
            exampleDialog.show(getSupportFragmentManager(), "error dialog");
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        final SearchView.OnQueryTextListener textListener = new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast toast = Toast.makeText(getApplicationContext(), "Searched: " + query, Toast.LENGTH_SHORT);
                toast.show();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        };

        searchView.setOnQueryTextListener(textListener);

        return super.onCreateOptionsMenu(menu);
    }
}
