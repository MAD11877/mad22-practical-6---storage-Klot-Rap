package sg.edu.np.mad.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

//this is linked with activity_user_obj
public class UserViewHolder extends RecyclerView.ViewHolder{
    TextView userName; //this is to ensure the msg stay even when the function ends.
    TextView userDesc;
    ImageView userImg;

    public UserViewHolder(View itemView) {
        super(itemView);
        userName = itemView.findViewById(R.id.userName); //viewItem represents the whole view in which the Recycler view is
        userDesc = itemView.findViewById(R.id.userDesc);
        userImg = itemView.findViewById(R.id.imageView);
    }

}
