package com.uiubd.brta_driving_exam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.uiubd.brta_driving_exam.utility.Config;
import com.uiubd.brta_driving_exam.utility.Question;

/**
 * Created by IntelliJ IDEA.
 * User: shahab
 * Date: 2/17/12
 * Time: 3:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class TaskListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] categoryname;
    private static final String TAG = TaskListAdapter.class.getSimpleName();

    public TaskListAdapter(Context context, String[] categoryname) {
        super(context, R.layout.list_items, categoryname);
        this.context = context;
        this.categoryname = categoryname;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_items, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.tvCategoryName);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.iconCheck);
        TextView textViewCompleteAns = (TextView) rowView.findViewById(R.id.tvCompleteAns);

        textView.setText(categoryname [position]);
        textViewCompleteAns.setText("(" + SetupApplicationActivity.answeredCnt [position] + "/" + Config.NUM_OF_QUES_PER_CATEGORY [position] + ")");

        //Log.d(TAG, "position: " + position);
        if ( SetupApplicationActivity.isCategoryCompleted [position] ) {
            imageView.setImageResource(R.drawable.check);
        }

        return rowView;
    }

}

