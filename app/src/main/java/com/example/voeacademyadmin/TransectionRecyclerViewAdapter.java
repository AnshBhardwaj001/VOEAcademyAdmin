package com.example.voeacademyadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voeacademyadmin.TransectionModal;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class TransectionRecyclerViewAdapter extends RecyclerView.Adapter<TransectionRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<TransectionModal> transections;
    public TransectionRecyclerViewAdapter(Context context, ArrayList<TransectionModal> transections) {
        this.context = context;
        this.transections = transections;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transections_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String teacher = transections.get(position).getTeacherName();
        String no_of_class = transections.get(position).getNo_of_classes();
        String subject = transections.get(position).getSubject();
        String selected_class = transections.get(position).getSelectedClass();
        String paymentId = transections.get(position).getPaymentID();
        String timestamp = transections.get(position).getTimeStamp();
        String amount = transections.get(position).getAmount();
        String  docID = transections.get(position).getDocID();
        String userID = transections.get(position).getUserID();
        String userName = transections.get(position).getUserName();
        String userMobileNo = transections.get(position).getUserMoblileNo();
        String userEmail = transections.get(position).getUserEmail();

        holder.paymentId.setText(paymentId);
        holder.teacherName.setText(teacher);
        holder.no_of_classes.setText(no_of_class);
        holder.selectedSubject.setText(subject);
        holder.selectedClass.setText(selected_class);
        holder.amount.setText(amount);
        holder.timestamp.setText(timestamp);
        holder.userID.setText(userID);
        holder.userName.setText(userName);
        holder.userMobleNo.setText(userMobileNo);
        holder.userEmail.setText(userEmail);
        final boolean[] completed = {transections.get(position).getIsClassesComplete()};
        if(!completed[0]){
            holder.isComplete.setText("On Going");
            holder.complete.setBackgroundResource(R.color.voe_color);
            holder.complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseFirestore voe_db_firestore = FirebaseFirestore.getInstance();
                    DocumentReference docRef = voe_db_firestore.collection("ClassBooking").document(docID);
                    docRef.update("isClassesCompleted", true);
                    holder.complete.setBackgroundResource(R.color.unread_message);
                    holder.isComplete.setText("Classes Completed");
                }
            });
        }else {
            holder.isComplete.setText("Classes Completed");
            holder.complete.setBackgroundResource(R.color.unread_message);
        }
    }


    @Override
    public int getItemCount() {
        return transections.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView teacherName, no_of_classes , selectedClass , selectedSubject , paymentId , timestamp,amount,userID,userName,userMobleNo,userEmail,isComplete;
//        LinearLayout cardView;
        Button complete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teacherName = itemView.findViewById(R.id.teacher_name);
            no_of_classes = itemView.findViewById(R.id.no_of_classes);
            selectedClass = itemView.findViewById(R.id.selected_class);
            selectedSubject = itemView.findViewById(R.id.selected_subject);
            paymentId = itemView.findViewById(R.id.payment_id);
            timestamp = itemView.findViewById(R.id.timestamp);
            amount = itemView.findViewById(R.id.amount);
//            cardView = itemView.findViewById(R.id.notification_cardview);
            userID = itemView.findViewById(R.id.userID);
            userName = itemView.findViewById(R.id.userName);
            userMobleNo = itemView.findViewById(R.id.userMobileNo);
            userEmail = itemView.findViewById(R.id.userEmail);
            isComplete = itemView.findViewById(R.id.isCompleted);
            complete = itemView.findViewById(R.id.completeButton);
        }
    }
}
