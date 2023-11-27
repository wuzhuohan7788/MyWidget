package com.inkbird.recyclerdemo.strackList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inkbird.recyclerdemo.R;
import com.inkbird.recyclerdemo.databinding.ItemStrackContentBinding;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class StrackListAdapter extends RecyclerView.Adapter<StrackListAdapter.ViewHolder> {
    private Context context;
    private List<AlertBean> alertBeanList;
    private onStrackListListener listener;
    private int ShowFlag = 1;

    public interface onStrackListListener {

        void onClose(AlertBean alertBean);
    }

    public StrackListAdapter(Context context, List<AlertBean> alertBeanList, onStrackListListener listener) {
        this.context = context;
        this.alertBeanList = alertBeanList;
        this.listener = listener;
    }

    public void setShowFlag(int showFlag) {
        this.ShowFlag = showFlag;
        notifyDataSetChanged();
    }

    public int getShowFlag() {
        return ShowFlag;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemStrackContentBinding binding =
                ItemStrackContentBinding.bind(LayoutInflater.from(context).inflate(R.layout.item_strack_content, viewGroup, false));
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ItemStrackContentBinding binding = viewHolder.binding;
        binding.ivTitleImg.setImageResource(alertBeanList.get(i).getImg());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        binding.content.setText(sdf.format(alertBeanList.get(i).getName()));
        binding.ivClose.setOnClickListener(v -> listener.onClose(alertBeanList.get(i)));
    }

    @Override
    public int getItemCount() {
        if (ShowFlag == 0) {
            return alertBeanList.size();
        } else {
            return 1;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemStrackContentBinding binding;

        public ViewHolder(@NonNull ItemStrackContentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
