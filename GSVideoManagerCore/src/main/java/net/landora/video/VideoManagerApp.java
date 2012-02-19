/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import net.landora.video.addons.AddonManager;
import net.landora.video.preferences.FileSyncProperties;
import net.landora.video.profile.ProfileSorter;
import net.landora.video.ui.ManagerProfile;
import net.landora.video.profile.RunProfile;
import net.landora.video.utils.EventBus;
import net.landora.video.utils.NamedThreadFactory;
import org.jdesktop.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class VideoManagerApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(VideoManagerApp.class);
    
    private FileSyncProperties localProperties;
    private ScheduledExecutorService scheduledExecutor;
    
    public static VideoManagerApp getInstance() {
        return getInstance(VideoManagerApp.class);
    }

    public VideoManagerApp() {
        profiles = new TreeMap<String, RunProfile>(String.CASE_INSENSITIVE_ORDER);
    }
    
    public synchronized FileSyncProperties getLocalProperties() {
        if (localProperties == null)
            localProperties = new FileSyncProperties("user.properties");
        return localProperties;
    }
    
    public synchronized ScheduledExecutorService getScheduledExecutor() {
        if (scheduledExecutor == null)
            scheduledExecutor = Executors.newScheduledThreadPool(2, new NamedThreadFactory("Scheduled", true));
        return scheduledExecutor;
    }
    
    private Map<String,RunProfile> profiles;
    
    private EventBus eventBus = new EventBus();

    private RunProfile profile;

    public EventBus getEventBus() {
        return eventBus;
    }

    public RunProfile getProfile() {
        return profile;
    }
    
    public synchronized void addProfile(RunProfile profile) {
        String name = profile.getProfileName();
        if (profiles.containsKey(name))
            throw new IllegalArgumentException("Profile with name already exists: " + name);
        profiles.put(name, profile);
    }
    
    @Override
    protected void startup() {
        
    }
    
    @Override
    protected void initialize(String[] args) {
        
        AddonManager.getInstance().loadAddons();
        
        String profileName;
        List<String> argsList = new ArrayList<String>(Arrays.asList(args));
        if (argsList.isEmpty()) {
            profileName = ManagerProfile.PROFILE_NAME;
        } else {
            profileName = argsList.remove(0);
        }
        
        if (profileName.equalsIgnoreCase("help")) {
            List<RunProfile> lists = new ArrayList<RunProfile>(profiles.values());
            Collections.sort(lists, new ProfileSorter());
            
            System.out.println("Avaliable profiles:");
            for(RunProfile p: lists)
                System.out.println("  " + p.getProfileName() + ": " + p.getProfileDescription());
            
            System.exit(0);
            return;
        }
        
        profile = profiles.get(profileName);
        if (profile == null) {
            log.error("No such profile: " + profileName);
            return;
        }
        
        if (!profile.readCommandLine(argsList.toArray(new String[argsList.size()]))) {
            profile = null;
            return;
        }
        
        AddonManager.getInstance().startAddons();
        
    }

    @Override
    protected void ready() {
        
        if (profile == null) {
            System.exit(-1);
            return;
        }
        
        AddonManager.getInstance().readyAddons();
        
        Integer returnCode = profile.runProfile();
        
        if (returnCode != null)
            System.exit(returnCode);
    }

    
    
    
}
