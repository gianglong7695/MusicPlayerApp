package app.dg.giang.dgplayer.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by Giang Long on 11/8/2018.
 * Skype: gianglong7695@gmail.com (id: gianglong7695_1)
 * Phone: 0979 579 283
 */
public class Utils {
    public static LinearLayoutManager getLinearLayoutManager(Context context, int type) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        if (type == 1) {
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        } else {
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        }

        return layoutManager;
    }
}
