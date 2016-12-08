package com.deshmukh.hrishikesh.bookpoint;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hrishikesh on 12/8/16.
 */

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder>{

    private List<BookList> listItems;
    private Context context;

    public BookListAdapter(List<BookList> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_list, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        BookList booklist = listItems.get(position);

        holder.mtitle.setText(booklist.getTitle());
        holder.price.setText(booklist.getPrice());
        holder.publisher.setText(booklist.getPublisher());
        holder.condition.setText(booklist.getCondition());
        holder.sold_by.setText(booklist.getSold_by());
        holder.ships_from.setText(booklist.getShips_from());


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mtitle;
        public TextView price;
        public TextView condition;
        public TextView publisher;
        public TextView sold_by;
        public TextView ships_from;
        public Button Buy_button;


        public ViewHolder(View itemView) {
            super(itemView);

            mtitle = (TextView) itemView.findViewById(R.id.book_title);
            price = (TextView) itemView.findViewById(R.id.book_price);
            condition = (TextView) itemView.findViewById(R.id.book_condition);
            publisher = (TextView) itemView.findViewById(R.id.book_publisher);
            sold_by = (TextView) itemView.findViewById(R.id.book_sold_by);
            ships_from = (TextView) itemView.findViewById(R.id.book_ships_from);
            Buy_button = (Button)  itemView.findViewById(R.id.buy_button);
        }
    }
}
