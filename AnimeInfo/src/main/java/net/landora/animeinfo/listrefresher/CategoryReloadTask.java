/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.listrefresher;

import net.landora.gsuiutils.NBTask;
import net.landora.animeinfo.anidb.AniDBHTTPManager;

/**
 *
 * @author bdickie
 */
public class CategoryReloadTask extends NBTask<Object, Object> {

    public CategoryReloadTask() {
        super("Loading Anime Categories");
    }

    
    
    @Override
    protected Object doInBackground() throws Throwable {
        start();
        
        AniDBHTTPManager.getInstance().updateCategoryNames();
        
        return null;
    }

    @Override
    protected void success(Object result) {
    }
    
}
