package com.example.taskmanagerproject.controller.activities;

import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.controller.fragments.TaskListFragment;
import com.example.taskmanagerproject.repository.TaskDBRepository;

public class SearchActivity extends SingleFragmentActivity {

    TaskDBRepository mRepository = TaskDBRepository.getInstance(this, 0);

    @Override
    public Fragment createFragment() {
        return TaskListFragment.newInstance(mRepository.getTasks(), 3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //todo
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //adapter.getFilter().filter(newText);
                return false;
            }
        });
        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_search:
                //todo
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}