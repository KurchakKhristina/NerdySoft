package example;

import java.util.HashSet;
import java.util.Set;

public class VirtualProductCodeManager {
    private Set<String> usedCodes;
    private static VirtualProductCodeManager instance;

    private VirtualProductCodeManager() {
        this.usedCodes = new HashSet<>();
    }

    public static VirtualProductCodeManager getInstance() {
        if (instance == null) {
            synchronized (VirtualProductCodeManager.class) {
                if (instance == null) {
                    instance = new VirtualProductCodeManager();
                }
            }
        }
        return instance;
    }

    public void useCode(String code) {
        usedCodes.add(code);
    }

    public boolean isCodeUsed(String code) {
        return usedCodes.contains(code);
    }
}
