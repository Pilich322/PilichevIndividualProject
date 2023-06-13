package com.example.pilichevindividualproject.Adapters;

import android.content.Context;
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
    private final List<Student> studentList;
    private DataBaseManager dbManager;
    private OnStudentClickListener onStudentClickListener;

    public interface OnStudentClickListener{
        void onStudentClick(Student student, int position);
    }
    public StudentAdapter(Context context, List<Student> studentList,OnStudentClickListener onStudentClickListener) {
        layoutInflater = LayoutInflater.from(context);
        this.studentList = studentList;
        dbManager = new DataBaseManager(context);
        this.onStudentClickListener = onStudentClickListener;
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
            dbManager.openDbToWrite();
            dbManager.deleteStudent(student);
            studentList.remove(position);
            notifyItemRemoved(position);
            dbManager.closeDb();
        });
        holder.change.setOnClickListener(v -> onStudentClickListener.onStudentClick(student,position));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView delete, change;
        TextView fName,sName,mName,birthday;
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
