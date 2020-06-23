package com.dommyg.conservativerss;

import android.os.Bundle;

/**
 * Provides methods for fragment-to-fragment and fragment-to-activity communication.
 */
public interface FragmentComm {

    /**
     * Allows for a fragment to inflate another fragment.
     * @param fragmentTag The fragment's tag, available from the hosting activity.
     * @param addToBackStack If the fragment should be added to the back stack, meaning that if
     *                       another fragment is added after this fragment, will this fragment be
     *                       left in the stack behind it (or will it be removed). Fragments in the
     *                       back stack can be navigated to in order of addition by the user
     *                       pressing the back button.
     * @param bundle Any fragment data which would be passed to the new fragment.
     */
    void inflateFragment(String fragmentTag, boolean addToBackStack, Bundle bundle);
}
