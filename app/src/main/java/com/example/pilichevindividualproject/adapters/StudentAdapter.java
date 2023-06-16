package com.example.pilichevindividualproject.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pilichevindividualproject.Data.Student;
import com.example.pilichevindividualproject.DataBase.DataBaseManager;
import com.example.pilichevindividualproject.R;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<Student> studentList;
    private OnDeleteClickListener onDeleteClickListener;
    private OnChangeClickListener onChangeClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(Student student, int position);
    }

    public interface OnChangeClickListener {
        void onChangeClick(Student student, int position);
    }

    public StudentAdapter(Context context, List<Student> studentList, OnDeleteClickListener onDeleteClickListener, OnChangeClickListener onChangeClickListener) {
        layoutInflater = LayoutInflater.from(context);
        this.studentList = studentList;
        this.onDeleteClickListener = onDeleteClickListener;
        this.onChangeClickListener = onChangeClickListener;
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.student_list_item, parent, false);
        return new StudentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.birthday.setText(student.getBirthday());
        holder.fName.setText(student.getFirstName());
        holder.sName.setText(student.getSecondName());
        holder.mName.setText(student.getMiddleName());
        holder.delete.setOnClickListener(v -> {
            onDeleteClickListener.onDeleteClick(student, position);
            notifyItemRemoved(position);
            studentList.remove(position);
            updateAdapter(studentList);
        });
        holder.change.setOnClickListener(v -> onChangeClickListener.onChangeClick(student, position));
    }

    public void updateAdapter(List<Student> newList) {
        this.studentList = newList;
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView delete, change;
        TextView fName, sName, mName, birthday;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.imageViewDeleteStudent);
            change = itemView.findViewById(R.id.imageViewChangeStudent);
            fName = itemView.findViewById(R.id.textViewStudentFirstName);
            sName = itemView.findViewById(R.id.textViewStudentSecondName);
            mName = itemView.findViewById(R.id.textViewStudentMiddleName);
            birthday = itemView.findViewById(R.id.textViewStudentBirthday);
        }
    }
}
