package com.engineer135.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2016-06-28.
 */
public class CheatActivity extends AppCompatActivity {

    // 다른 앱의 엑스트라와 이름이 충돌되는 것을 방지하기 위해 수식자(qualifier)로 패키지 이름을 사용.
    public static final String EXTRA_ANSWER_IS_TRUE = "com.engineer135.geoquiz.answer_is_true";

    // 정답 여부
    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;
    private Button mShowAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        // 넘어온 값이 있으면 변경해준다.
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        // 정답인지 보여줄 텍스트뷰
        mAnswerTextView = (TextView) findViewById(R.id.answerTextView);

        // 정답 보기 버튼에 리스너 달자
        mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }else{
                    mAnswerTextView.setText(R.string.false_button);
                }
            }
        });
    }
}
