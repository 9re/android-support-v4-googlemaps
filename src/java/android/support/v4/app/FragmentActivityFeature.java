package android.support.v4.app;

import android.content.Intent;
import android.os.Handler;

public interface FragmentActivityFeature {
    /***
     * methods from the original FragmentActivity
     ***/
    Object onRetainCustomNonConfigurationInstance();
    Object getLastCustomNonConfigurationInstance();
    void supportInvalidateOptionsMenu();
    void onAttachFragment(Fragment fragment);
    FragmentManager getSupportFragmentManager();
    void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode);
    void invalidateSupportFragmentIndex(int index);
    LoaderManager getSupportLoaderManager();
    LoaderManagerImpl getLoaderManager(int index, boolean started, boolean create);
    
    /*** 
     * getter for the fields of original FragmentActivity
     * which are accessed internally in the package.
     ***/
    FragmentManagerImpl<?> getFragmentManagerImpl();
    boolean isRetaining();
    Handler getHandler();
}
