package atlas.com.trainerapp.authentication.presenters.fragmentPresenters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import atlas.com.trainerapp.authentication.models.User;
import atlas.com.trainerapp.bases.BasePresenter;
import atlas.com.trainerapp.managers.DataManager;
import atlas.com.trainerapp.widgets.TATextView;

/**
 * Created by paulo.losbanos on 18/08/2016.
 */
public class RegisterPresenter extends BasePresenter {

    String userName;
    String password;
    String email;

    public FirebaseAuth mAuth;

    public RegisterPresenter(FragmentActivity fa, LinearLayout llMainBody) {
        super(fa, llMainBody);
        mAuth = FirebaseAuth.getInstance();
    }

    public void setUsername(CharSequence uname) {
        this.userName = uname.toString();
    }

    public void setPassword(CharSequence pword) {
        this.password = pword.toString();
    }

    public void setEmail(CharSequence email) {
        this.email = email.toString();
    }

    public void clickRegister() {
        if (email.isEmpty() || password.isEmpty() || userName.isEmpty()) {
            Snackbar snackbar = Snackbar.make(mParentView, "Please fill out the form correctly.", Snackbar.LENGTH_INDEFINITE);
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
            snackbar.setAction("Okay", temp);
            TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTypeface(new TATextView(mContext).typeFace());
            textView.setGravity(Gravity.CENTER);
            snackbar.show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            Log.e(TAG, "success");
                            createUserEntry(task.getResult().getUser());
                        } else {
                            Log.e(TAG, "fail");
                        }
                    }
                });
    }

    private void createUserEntry(FirebaseUser user) {
        User newUser = generateNewUser(user);
        DataManager.getInstance()
                .addData(newUser)
                .subscribe(aBoolean -> Log.e(TAG, "Creating new user: success"));
    }

    private User generateNewUser(FirebaseUser user) {
        User newUser = new User(user.getUid());
        newUser.setUsername(userName);
        newUser.setGroup("none");
        newUser.setTeams(null);
        newUser.setFirstTimeLogin("true");
        newUser.setUserType("normal");
        return newUser;
    }

    View.OnClickListener temp = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

}
