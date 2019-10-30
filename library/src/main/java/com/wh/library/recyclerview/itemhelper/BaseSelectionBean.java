package com.wh.library.recyclerview.itemhelper;

public abstract class BaseSelectionBean {

    public static final int FLAG_SELECTED = 1;
    private int mFlags;

    public boolean hasStateFlags(int flags) {
        return (mFlags & flags) != 0;
    }

    public void toggleStateFlag(int flag) {
        if (hasStateFlags(flag)) {
            removeStateFlags(flag);
        } else {
            addStateFlags(flag);
        }
    }

    public void removeStateFlags(int flags) {
        mFlags &= ~flags;
    }

    public void addStateFlags(int flags) {
        mFlags |= flags;
    }

    public boolean isSelected(){
        return hasStateFlags(FLAG_SELECTED);
    }
}
