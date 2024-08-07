package sk.adr3ez.armcore.api;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;

public interface ArmCore {

    //Methods
    JavaPlugin getJavaPlugin();

    class INSTANCE {

        private static ArmCore instance;

        private INSTANCE() {
            throw new RuntimeException("This class cannot be utilized!");
        }

        public static ArmCore get() {
            if (instance == null)
                throw new UnsupportedOperationException("ArmCore is not loaded yet!");
            return instance;
        }

        @ApiStatus.Internal
        public static void set(ArmCore inst) {
            instance = inst;
        }

    }

}
