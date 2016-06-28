package com.engineer135.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index"; // 번들 객체에 저장될 키와 값 쌍에서 키가 될 값을 상수로 추가

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;

    // 치트 버튼 추가
    private Button mCheatButton;

    // 치트 여부
    private boolean mIsCheater;

    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

        int messageResId = 0;

        // 커닝한 경우 추가
        if(mIsCheater){
            messageResId = R.string.judgment_toast;
        }else{
            if(userPressedTrue == answerIsTrue){
                messageResId = R.string.correct_toast;
            }else{
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 번들 객체에 저장해둔 값이 있다면 그 값으로 변경!
        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        // 로그 추가
        Log.d(TAG, "onCreate(Bundle) called");

        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        // 질문 텍스트도 누르면 다음으로 넘어가도록 추가
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        // 위젯의 리소스 id를 인자로 받아서 View 객체 반환
        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                checkAnswer(true);
            }
        });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                checkAnswer(false);
            }
        });

        // 다음 질문으로 가기
        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        // 이전 질문으로 가기도 추가
        mPrevButton = (ImageButton)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // 이게 최선일까.. 고민해보자;;
                if(mCurrentIndex -1 < 0){
                    mCurrentIndex = mQuestionBank.length - 1;
                }else{
                    mCurrentIndex = (mCurrentIndex - 1);
                }
                mIsCheater = false;
                updateQuestion();
            }
        });

        // 치트 버튼 리스너 추가
        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //치트 액티비티를 시작시켜야 한다.

                // 1단계. 명시적 인텐트(같은 어플리케이션의 다른 액티비티 호출할때 사용)
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);

                // 인텐트 엑스트라를 이용해 값을 전달한다.
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);

                //startActivity(i);

                //자식 액티비티로부터 데이터를 돌려받고 싶을땐 startActivity가 아닌
                startActivityForResult(i, 0);
            }
        });

        updateQuestion();
    }

    // 화면 전환시 현재 값을 저장해두려면 onSaveInstanceState 메소드를 호출해야한다.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    // 자식 액티비티에서 부모 액티비티로 돌아오면 자식이 준 데이터를 확인하는 메소드를 호출한다.
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.d(TAG, "부모창으로 돌아왔으므로 데이터 확인");
        if(data == null){
            return;
        }
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
