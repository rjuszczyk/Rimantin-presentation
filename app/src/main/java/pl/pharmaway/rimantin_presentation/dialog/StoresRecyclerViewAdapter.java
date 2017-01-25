package pl.pharmaway.rimantin_presentation.dialog;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.pharmaway.rimantin_presentation.R;
import pl.pharmaway.rimantin_presentation.model.PharmacyDataRow;

/**
 * Created by Radek on 2017-01-14.
 */

public abstract class StoresRecyclerViewAdapter extends RecyclerView.Adapter<StoresRecyclerViewAdapter.StoreViewHolder> implements View.OnClickListener, SectionIndexer {

    private List<PharmacyDataRow> mStores = new ArrayList<>();
    private List<PharmacyDataRow> mStoresCache = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    private int mLastPosition = -1;

    public StoresRecyclerViewAdapter() {
    }

    public StoresRecyclerViewAdapter(List<PharmacyDataRow> stores) {
        mStores.clear();
        mStoresCache.clear();
        Collections.sort(stores, getRowComparator());
        mStores.addAll(stores);
        mStoresCache.addAll(mStores);
    }

    public Comparator<PharmacyDataRow> getRowComparator() {
        return new Comparator<PharmacyDataRow>() {
            @Override
            public int compare(PharmacyDataRow lhs, PharmacyDataRow rhs) {
                return getTextFromRow(lhs).compareToIgnoreCase(getTextFromRow(rhs));
            }
        };
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    public boolean isTwoLinesAdapter() {
        return false;
    }
    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(isTwoLinesAdapter()) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_two_lines, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        }
        v.setOnClickListener(this);
        return new StoreViewHolder(v);
    }

    public abstract String getTextFromRow(PharmacyDataRow row);

    @Override
    public void onBindViewHolder(StoreViewHolder holder, int position) {
        PharmacyDataRow store = mStores.get(position);
        holder.mStoreName.setText(getTextFromRow(store));

        if(isTwoLinesAdapter()) {
            holder.mSecondLine.setText(store.getUlica());
        }

        holder.itemView.setTag(store);
    }

    @Override
    public int getItemCount() {
        return mStores.size();
    }

    @Override
    public void onClick(final View v) {
        // Give some time to the ripple to finish the effect
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, ((PharmacyDataRow) v.getTag()));
        }
    }

    @Override
    public Object[] getSections() {
        return mStores.toArray();
    }

    @Override
    public int getPositionForSection(int i) {
        return 0;
    }

    @Override
    public int getSectionForPosition(int i) {
        if (i >= mStores.size() - 1) {
            return mStores.size() - 1;
        } else {
            return i;
        }
    }

    public void setStores(List<PharmacyDataRow> stores) {
        mStores.clear();
        mStoresCache.clear();
        Collections.sort(stores, getRowComparator());
        mStores.addAll(stores);
        mStoresCache.addAll(mStores);
        notifyDataSetChanged();
    }

    public void sortStores(Comparator<PharmacyDataRow> comparator) {
        Collections.sort(mStores, comparator);
        notifyDataSetChanged();
    }

    public void filter(String query) {
        mStores.clear();
        for (PharmacyDataRow store : mStoresCache) {
            if (query.length() < 2 || getTextFromRow(store).toLowerCase().contains(query.toLowerCase())) {
                mStores.add(store);
            }
        }
        notifyDataSetChanged();
    }



    protected static class StoreViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        public TextView mStoreName;

        @Nullable
        @BindView(R.id.secondLine)
        public TextView mSecondLine;
        public StoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, PharmacyDataRow row);
    }
}
