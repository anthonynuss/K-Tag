//package com.example.testgps;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.android.volley.Response;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class LeaderBoardAdapter extends ArrayAdapter<JSONObject> {
//
//    private Context mContext;
//    private ArrayList <JSONObject> users;
//    private int mResource;
//    private int lastPosition = -1;
//
//    /**
//     * Holds variables in a View
//     */
//    private static class ViewHolder {
//        TextView name;
//        TextView birthday;
//        TextView sex;
//    }
//
//    public LeaderBoardAdapter(Context context, int resource, ArrayList<JSONObject> objects) {
//        super(context, resource, objects);
//        mContext = context;
//        mResource = resource;
//        users = objects;
//    }
//
//    @Override
//    public int getCount() {
//        return 0;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        //get the persons information
//        String name = users.getJSONObject(1);
//        String birthday = getItem(position).getBirthday();
//        String sex = getItem(position).getSex();
//
//        //Create the person object with the information
//        //JSONObject users = new JSONObject();
//
//        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
//        TextView tvBirthday = (TextView) convertView.findViewById(R.id.textView2);
//        TextView tvSex = (TextView) convertView.findViewById(R.id.textView3);
//
//        tvName.setText(name);
//        tvBirthday.setText(birthday);
//        tvSex.setText(sex);
//
//        //create the view result for showing the animation
//        final View result;
//
//        //ViewHolder object
//        ViewHolder holder;
//}
