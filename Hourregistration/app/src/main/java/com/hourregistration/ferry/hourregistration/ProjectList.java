package com.hourregistration.ferry.hourregistration;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ProjectList extends ArrayAdapter<Project> {

    private Activity context;
    List<Project> projects;

    public ProjectList(Activity context, List<Project> projects) {
        super(context, R.layout.layout_project_list, projects);
        this.context = context;
        this.projects = projects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_project_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);

        Project artist = projects.get(position);
        textViewName.setText(artist.getProjectName());

        return listViewItem;
    }
}
