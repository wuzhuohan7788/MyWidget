package com.inkbird.recyclerdemo.strackList;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.inkbird.recyclerdemo.R;
import com.inkbird.recyclerdemo.databinding.ActivityStrackListDemoBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StrackListDemoActivity extends AppCompatActivity implements StrackListAdapter.onStrackListListener {
    private ActivityStrackListDemoBinding binding;
    private StrackListAdapter adapter;
    private long id = 0;

    private List<AlertBean> packUpList = new ArrayList<>();

    private boolean isShowAll = false;
    RelativeLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStrackListDemoBinding.bind(LayoutInflater.from(this).inflate(R.layout.activity_strack_list_demo, null, true));
        setContentView(binding.getRoot());
        params = (RelativeLayout.LayoutParams) binding.layoutList.getLayoutParams();

        initView();
        initViewListener();
    }

    private void initViewListener() {
        binding.tvShowAll.setOnClickListener(v -> {
            isShowAll = adapter.getShowFlag() == 0;
            if (isShowAll) {
                adapter.setShowFlag(1);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            } else {
                adapter.setShowFlag(0);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            }
            binding.layoutList.setLayoutParams(params);
        });

        binding.tvAdd.setOnClickListener(v -> {
            refreshData(true);
        });

        binding.tvRemove.setOnClickListener(v -> {
            refreshData(false);
        });
    }

    private void refreshData(boolean isAdd) {
        if (isAdd) {
            if (packUpList != null) {
                packUpList.add(newAlertData(System.currentTimeMillis()));
                if (!packUpList.isEmpty()) {
                    binding.layoutList.setVisibility(View.VISIBLE);
                }
            }
        } else {
            if (packUpList != null) {
                if (!packUpList.isEmpty()) {
                    int length = packUpList.size() - 1;
                    packUpList.remove(length);
                } else {
                    binding.layoutList.setVisibility(View.GONE);
                }
            }
        }
        Collections.reverse(packUpList);
        adapter.notifyDataSetChanged();
    }

    private AlertBean newAlertData(long currentTime) {
        id += 1;
        return new AlertBean(id, R.mipmap.ic_launcher, currentTime);
    }

    private void initView() {
        packUpList.add(newAlertData(System.currentTimeMillis()));
        adapter = new StrackListAdapter(this, packUpList, this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClose(AlertBean alertBean) {

    }
}