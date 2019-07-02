package com.example.android.shop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.android.shop.R;
import com.example.android.shop.SendHttpRequestTask;
import com.example.android.shop.model.Questions;

import java.util.List;

public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.DynamicView> {
    List<Questions> questionsList;
    Context context;

    public DynamicAdapter(List<Questions> questionsList, Context context) {
        this.questionsList = questionsList;
        this.context=context;
    }


    @NonNull
    @Override
    public DynamicView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dynamic_list_adapter, viewGroup, false);
        return new DynamicView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DynamicView dynamicView, int position) {
        String IMAGE_URL= "http://159.65.93.198:3001/api/question/images/";
        Questions questions= questionsList.get(position);
        dynamicView.questionDetail.setText(questions.getQuestionDetail());

        String questionImage= questions.getQuestionImage();
        if (questionImage!=null&& !questionImage.equals("null")&& !TextUtils.isEmpty(questionImage))
        {
            dynamicView.questionImage.setVisibility(View.VISIBLE);
            new SendHttpRequestTask(dynamicView.questionImage).execute(IMAGE_URL+questionImage);
        }

        dynamicView.setOptions(questions,position);

    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    public class DynamicView extends RecyclerView.ViewHolder {
        private TextView questionDetail;
        private ImageView questionImage;
        private LinearLayout radioHolder;
        RadioGroup radioGroupoptions;

        public DynamicView(@NonNull View itemView) {
            super(itemView);
            questionDetail = (TextView) itemView.findViewById(R.id.questionDetail);
            questionImage = (ImageView) itemView.findViewById(R.id.questionImage);
            radioHolder = (LinearLayout) itemView.findViewById(R.id.radioHolder);
        }
        public void setOptions(Questions questions,int position)
        {
            radioGroupoptions= new RadioGroup(context);

            radioHolder.removeAllViews();
            radioGroupoptions.setTag(position);
            for(int i=0;i<questions.getAnswersList().size();i++)
            {
                String correctAnswer= questions.getAnswersList().get(i).getCorrectAnswer();
                String answerImage= questions.getAnswersList().get(i).getAnsweredImage();
                RadioButton radioButton= new RadioButton(context);
                radioButton.setText(questions.getAnswersList().get(i).getAnswer());
                radioButton.setId(i);

                radioGroupoptions.addView(radioButton);
            }
            radioHolder.addView(radioGroupoptions,radioHolder.getChildCount());
        }
    }

}
