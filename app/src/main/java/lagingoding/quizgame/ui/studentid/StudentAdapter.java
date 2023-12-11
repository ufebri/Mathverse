package lagingoding.quizgame.ui.studentid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lagingoding.quizgame.R;
import lagingoding.quizgame.data.local.entity.Student;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> studentList;
    private Context context;
    private onClickItemListener onClickItemListener;

    public interface onClickItemListener {
        void onClickDelete(String nim);

        void onClickEdit(String nim);
    }

    public StudentAdapter(List<Student> studentList, Context context, onClickItemListener onClickItemListener) {
        this.studentList = studentList;
        this.context = context;
        this.onClickItemListener = onClickItemListener;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_data_siswa, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);

        holder.tvNim.setText(student.nim);
        holder.tvName.setText(student.name);
        holder.tvProgram.setText(student.program);
        holder.tvNoHp.setText(student.nohp);
        holder.tvEmail.setText(student.email);

        holder.btEdit.setOnClickListener(v -> onClickItemListener.onClickEdit(student.nim));
        holder.btDelete.setOnClickListener(v -> onClickItemListener.onClickDelete(student.nim));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView tvNim, tvName, tvProgram, tvNoHp, tvEmail;
        Button btEdit, btDelete;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNim = itemView.findViewById(R.id.tv_nim);
            tvName = itemView.findViewById(R.id.tv_name);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvProgram = itemView.findViewById(R.id.tv_program);
            tvNoHp = itemView.findViewById(R.id.tv_nohp);
            btDelete = itemView.findViewById(R.id.btn_delete);
            btEdit = itemView.findViewById(R.id.btn_edit);
        }
    }
}
