package app.dg.giang.dgplayer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.dg.giang.dgplayer.R;
import app.dg.giang.dgplayer.tutorial.OnClickListenerPosition;
import app.dg.giang.dgplayer.tutorial.Song;
import app.dg.giang.dgplayer.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Giang Long on 11/5/2018.
 * Skype: gianglong7695@gmail.com (id: gianglong7695_1)
 * Phone: 0979 579 283
 */
public class PlayListAudioAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Song> listAudio;
    //    private PlayerHolder playerHolder;
    private int currentPlayPos;
    private View.OnClickListener onClickListener;
    private OnClickListenerPosition onClickListenerPosition;


    public PlayListAudioAdapter(Context context, List<Song> listAudio) {
        this.context = context;
        this.listAudio = listAudio;

//        playerHolder = new PlayerHolder(context);
    }


    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    public void setOnClickListenerPosition(OnClickListenerPosition onClickListenerPosition) {
        this.onClickListenerPosition = onClickListenerPosition;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_playlist_audio, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        ((ViewHolder) holder).setData(listAudio.get(i));
    }

    @Override
    public int getItemCount() {
        return listAudio.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ic_state)
        ImageView ic_state;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_time)
        TextView tv_time;


        private Song song;
        private View view;

        private ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
//            playerHolder.setPlayerListener(this);
            view.setOnClickListener(onClickListener);
        }

        private void setData(Song song) {
            this.song = song;
            tv_name.setText(song.getTitle());

            if(song.getDuration() != 0){
                tv_time.setText(Utils.formatDuration(song.getDuration()));
            }


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListenerPosition.onClickPosition(getAdapterPosition());
                }
            });


            view.setTag(song);

        }

    }

    public void resetDefault() {
//            for (int i = 0; i < listAudio.size(); i++) {
//                listAudio.get(i).setPlaying(false);
//            }
//
//            notifyDataSetChanged();
    }


}
