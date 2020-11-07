package xyz.wagyourtail.jsmacros.api.functions;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import xyz.wagyourtail.jsmacros.JsMacros;
import xyz.wagyourtail.jsmacros.api.classes.Draw2D;
import xyz.wagyourtail.jsmacros.api.classes.Draw3D;
import xyz.wagyourtail.jsmacros.api.classes.Screen;
import xyz.wagyourtail.jsmacros.api.sharedinterfaces.IDraw2D;
import xyz.wagyourtail.jsmacros.api.sharedinterfaces.IScreen;
import xyz.wagyourtail.jsmacros.extensionbase.Functions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * Functions for displaying stuff in 2 to 3 dimensions
 * 
 * An instance of this class is passed to scripts as the {@code hud} variable.
 * 
 * @since 1.0.5
 * 
 * @author Wagyourtail
 *
 */
public class FHud extends Functions {
    /**
     * Don't touch this here
     */
    public static final Set<IDraw2D<Draw2D>> overlays = new HashSet<>();
    /**
     * Don't touch this here
     */
    public static final Set<Draw3D> renders = new HashSet<>();
    
    public FHud(String libName) {
        super(libName);
    }
    
    public FHud(String libName, List<String> excludeLanguages) {
        super(libName, excludeLanguages);
    }
    
    /**
     * 
     * @see xyz.wagyourtail.jsmacros.api.sharedinterfaces.IScreen
     * 
     * @since 1.0.5
     * 
     * @param title
     * @param dirtBG boolean of whether to use a dirt background or not.
     * @return a new {@link xyz.wagyourtail.jsmacros.api.sharedinterfaces.IScreen IScreen} Object.
     */
    public IScreen createScreen(String title, boolean dirtBG) {
        return (IScreen) new Screen(title, dirtBG);
    }
    
    /**
     * Opens a {@link xyz.wagyourtail.jsmacros.api.sharedinterfaces.IScreen IScreen} Object.
     * 
     * @since 1.0.5
     * 
     * @see xyz.wagyourtail.jsmacros.api.sharedinterfaces.IScreen
     * 
     * @param s
     */
    public void openScreen(IScreen s) {
        net.minecraft.client.gui.screen.Screen screen = (net.minecraft.client.gui.screen.Screen) s;
        mc.execute(() -> {
            mc.openScreen(screen);
        });
    }
    
    /**
     * 
     * @since 1.2.7
     * 
     * @see xyz.wagyourtail.jsmacros.api.sharedinterfaces.IScreen
     * 
     * @return the currently open Screen as an {@link xyz.wagyourtail.jsmacros.api.sharedinterfaces.IScreen IScreen}
     */
    public IScreen getOpenScreen() {
        return (IScreen) mc.currentScreen;
    }
    
    /**
     * 
     * @since 1.0.5, renamed from {@code getOpenScreen} in 1.2.7
     * 
     * @return The name of the currently open screen.
     */
    public String getOpenScreenName() {
        return JsMacros.getScreenName(mc.currentScreen);
    }
    
    /**
     * 
     * @since 1.1.2
     * 
     * @return a {@link java.lang.Boolean boolean} denoting if the currently open screen is a container. 
     */
    public boolean isContainer() {
        return mc.currentScreen instanceof HandledScreen;
    }
    
    
    /**
     * @since 1.0.5
     * 
     * @see xyz.wagyourtail.jsmacros.api.sharedinterfaces.IDraw2D
     * 
     * @return a new {@link xyz.wagyourtail.jsmacros.api.sharedinterfaces.IDraw2D IDraw2D}.
     */
    public IDraw2D<Draw2D> createDraw2D() {
        return new Draw2D();
    }
    
    /**
     * @since 1.0.5
     * 
     * Registers an {@link xyz.wagyourtail.jsmacros.api.sharedinterfaces.IDraw2D IDraw2D} to be rendered.
     * 
     * @see xyz.wagyourtail.jsmacros.api.sharedinterfaces.IDraw2D
     * 
     * @param overlay
     */
    public void registerDraw2D(IDraw2D<Draw2D> overlay) {
        overlay.init();
        synchronized (overlays) {
            overlays.add(overlay);
        }
    }
    
    /**
     * @since 1.0.5
     * 
     * Unregisters an {@link xyz.wagyourtail.jsmacros.api.sharedinterfaces.IDraw2D IDraw2D} to stop it being rendered.
     * 
     * @see xyz.wagyourtail.jsmacros.api.sharedinterfaces.IDraw2D
     * 
     * @param overlay
     */
    public void unregisterDraw2D(Draw2D overlay) {
        synchronized (overlays) {
            overlays.remove(overlay);
        }
    }
    
    /**
     * @since 1.0.5
     * 
     * @see xyz.wagyourtail.jsmacros.api.sharedinterfaces.IDraw2D
     * 
     * @return A list of current {@link xyz.wagyourtail.jsmacros.api.sharedinterfaces.IDraw2D IDraw2Ds}.
     */
    public List<IDraw2D<Draw2D>> listDraw2Ds() {
        return ImmutableList.copyOf(overlays);
    }
    
    /**
     * @since 1.0.5
     * 
     * clears the Draw2D render list.
     * 
     * @see xyz.wagyourtail.jsmacros.api.sharedinterfaces.IDraw2D
     */
    public void clearDraw2Ds() {
        synchronized (overlays) {
            overlays.clear();
        }
    }
    
    /**
     * @since 1.0.6
     * 
     * @see xyz.wagyourtail.jsmacros.api.classes.Draw3D
     * 
     * @return a new {@link xyz.wagyourtail.jsmacros.api.classes.Draw3D Draw3D}.
     */
    public Draw3D createDraw3D() {
        return new Draw3D();
    }
    
    /**
     * @since 1.0.6
     * 
     * Registers an {@link xyz.wagyourtail.jsmacros.api.classes.Draw3D Draw3D} to be rendered.
     * 
     * @see xyz.wagyourtail.jsmacros.api.classes.Draw3D
     * 
     * @param draw
     */
    public void registerDraw3D(Draw3D draw) {
        synchronized (renders) {
            renders.add(draw);
        }
    }
    
    /**
     * @since 1.0.6
     * 
     * Unregisters an {@link xyz.wagyourtail.jsmacros.api.classes.Draw3D Draw3D} to stop it being rendered.
     * 
     * @see xyz.wagyourtail.jsmacros.api.classes.Draw3D
     * 
     * @param draw
     */
    public void unregisterDraw3D(Draw3D draw) {
        synchronized (renders) {
            renders.remove(draw);
        }
    }
    
    /**
     * @since 1.0.6
     * 
     * @see xyz.wagyourtail.jsmacros.api.classes.Draw3D
     * 
     * @return A list of current {@link xyz.wagyourtail.jsmacros.api.classes.Draw3D Draw3D}.
     */
    public List<Draw3D> listDraw3Ds() {
        return ImmutableList.copyOf(renders);
    }
    
    /**
     * @since 1.0.6
     * 
     * clears the Draw2D render list.
     * 
     * @see xyz.wagyourtail.jsmacros.api.classes.Draw3D
     */
    public void clearDraw3Ds() {
        synchronized (renders) {
            renders.clear();
        }
    }
    
    /**
     * @since 1.1.3
     * 
     * @return the current X coordinate of the mouse
     */
    public double getMouseX() {
        return mc.mouse.getX() * (double)mc.getWindow().getScaledWidth() / (double)mc.getWindow().getWidth();
    }
    
    /**
     * @since 1.1.3
     * 
     * @return the current Y coordinate of the mouse
     */
    public double getMouseY() {
        return mc.mouse.getY() * (double)mc.getWindow().getScaledHeight() / (double)mc.getWindow().getHeight();
    }
}