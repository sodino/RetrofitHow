package com.sodino.retrofit.bean;

/**
 * Created by Administrator on 2016/2/4.
 */
public class FollowDepartment extends BaseEn {
    public static final String COMMUNITY_FOLLOW_DEPARTMENT = "community_follow_department";
    public static final String WEIBO_FOLLOW_DEPARTMENT = "weibo_follow_department";
    public static final String MANAGER_FOLLOW_DEPARTMENT = "manager_follow_department";
    public static final int NONE = -1;
    public static final int STATE_UNFOCUSED = 0;
    public static final int UI_FOCUSING = 1;
    public static final int STATE_FOCUSED = 2;
    public static final int UI_UNFOCUSING = 3;
    public int id;
    public String name;
    public boolean has_subdivision;

    /**
     * {@link #STATE_FOCUSED},
     * {@link #UI_FOCUSING},
     */
    public int state = FollowDepartment.STATE_FOCUSED;
    /**
     * {@link #NONE},
     * {@link #STATE_UNFOCUSED},
     * {@link #UI_UNFOCUSING}
     */
    public transient int stateUI = NONE;

    @Override
    public boolean equals(Object o) {
        if(null == o || !(o instanceof FollowDepartment)){
            return false;
        }
        else if(((FollowDepartment) o).id == this.id){
            return true;
        }
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
