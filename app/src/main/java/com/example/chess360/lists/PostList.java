package com.example.chess360.lists;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.chess360.R;
import com.example.chess360.vo.Post;

import java.util.ArrayList;

public class PostList extends BaseAdapter {

    Context context;
    private ArrayList<Post> posts;
    public PostList(Context context, ArrayList<Post> userPosts){
        // super(context,  R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.posts = userPosts;
    }
    @Override
    public int getCount() {
        return posts.size();
    }
    @Override
    public Object getItem(int i) {
        return i;
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    public Post getPost(int i){
        return(posts.get(i));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.view_post, parent, false);

            viewHolder.postUsername = (TextView) convertView.findViewById(R.id.post_username);
            viewHolder.postDate = (TextView) convertView.findViewById(R.id.post_date);
            viewHolder.postText = (TextView) convertView.findViewById(R.id.post_text);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.postUsername.setText("@" + posts.get(position).getUser().getUsername());
        viewHolder.postDate.setText(posts.get(position).getDate().toString());
        viewHolder.postText.setText(posts.get(position).getText());
/*
        if (flights.get(position).getEmptySeats() != 0){
            viewHolder.seats.setText(Integer.toString(flights.get(position).getEmptySeats()) + " "
                    + context.getResources().getString(R.string.flight_seats) + " " +
                    Integer.toString(flights.get(position).getSeats()));
        }
        else{
            viewHolder.seats.setText(context.getResources().getString(R.string.error_full_flight));
        }

        viewHolder.price.setText(Integer.toString(flights.get(position).getPrice()) +
                context.getResources().getString(R.string.flight_coin));

        int pos = Dao.getFlights().indexOf(new Flight(flights.get(position).getCode()));
        String route = Dao.getFlights().get(pos).getRoute();
        int routePosition = Dao.getRoutes().indexOf(new Route(route));
        String date = flights.get(position).getDate();

        viewHolder.direction.setText(Dao.getRoutes().get(routePosition).getOrigin() + " " +
                context.getResources().getString(R.string.search_connector) + " " +
                Dao.getRoutes().get(routePosition).getDestination() + " on " + date);

        viewHolder.stops.setText(Integer.toString(flights.get(position).getNumStops()) +
                " " + context.getResources().getString(R.string.flight_stops));
*/
        return convertView;
    }
    private static class ViewHolder {
        TextView postUsername;
        TextView postDate;
        TextView postText;
    }
}
