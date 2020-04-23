package com.jungel.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TestFragment extends Fragment {

    private View mView;
    private RecyclerView mRecyclerView;

    public static TestFragment newInstance() {

        Bundle args = new Bundle();

        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_test, null);
            mRecyclerView = mView.findViewById(R.id.recycle);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.setAdapter(new RecyclerView.Adapter() {
                @NonNull
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                  int viewType) {
                    return new RecyclerView.ViewHolder(new TextView(getContext())) {

                    };
                }

                @Override
                public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,
                                             int position) {
                    ((TextView) holder.itemView).setText(String.valueOf(position));
                }

                @Override
                public int getItemCount() {
                    return 100;
                }
            });
        }
        return mView;
    }
}
