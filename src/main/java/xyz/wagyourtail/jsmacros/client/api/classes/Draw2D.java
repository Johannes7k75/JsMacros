package xyz.wagyourtail.jsmacros.client.api.classes;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import xyz.wagyourtail.jsmacros.client.api.helpers.ItemStackHelper;
import xyz.wagyourtail.jsmacros.client.api.helpers.TextHelper;
import xyz.wagyourtail.jsmacros.client.api.sharedclasses.RenderCommon;
import xyz.wagyourtail.jsmacros.client.api.sharedinterfaces.IDraw2D;
import xyz.wagyourtail.jsmacros.core.MethodWrapper;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Wagyourtail
 *
 * @since 1.0.5
 *
 * @see xyz.wagyourtail.jsmacros.client.api.sharedinterfaces.IDraw2D
 */
public class Draw2D extends DrawableHelper implements IDraw2D<Draw2D> {
    private final Set<Drawable> elements = new LinkedHashSet<>();
    /**
     * @since 1.0.5
     * @deprecated please use {@link Draw2D#setOnInit(MethodWrapper)}
     */
    public MethodWrapper<Draw2D, Object, Object> onInit;
    /**
     * @since 1.1.9 [citation needed]
     * @deprecated please use {@link Draw2D#setOnFailInit(MethodWrapper)}
     */
    public MethodWrapper<String, Object, Object> catchInit;
    
    protected final MinecraftClient mc;
    
    public Draw2D() {
        this.mc = MinecraftClient.getInstance();
    }
    
    /**
     * @since 1.0.5
     * @see IDraw2D#getWidth()
     */
    @Override
    public int getWidth() {
        return mc.getWindow().getScaledWidth();
    }

    /**
     * @since 1.0.5
     * @see IDraw2D#getHeight()
     */
    @Override
    public int getHeight() {
        return mc.getWindow().getScaledHeight();
    }

    /**
     * @since 1.0.5
     * @see IDraw2D#getTexts()
     */
    @Override
    public List<RenderCommon.Text> getTexts() {
        List<RenderCommon.Text> list = new LinkedList<>();
        synchronized (elements) {
            for (Drawable e : elements) {
                if (e instanceof RenderCommon.Text) list.add((RenderCommon.Text) e);
            }
        }
        return list;
    }

    /**
     * @since 1.0.5
     * @see IDraw2D#getRects()
     */
    @Override
    public List<RenderCommon.Rect> getRects() {
        List<RenderCommon.Rect> list = new LinkedList<>();
        synchronized (elements) {
            for (Drawable e : elements) {
                if (e instanceof RenderCommon.Rect) list.add((RenderCommon.Rect) e);
            }
        }
        return list;
    }

    /**
     * @since 1.0.5
     * @see IDraw2D#getItems()
     */
    @Override
    public List<RenderCommon.Item> getItems() {
        List<RenderCommon.Item> list = new LinkedList<>();
        synchronized (elements) {
            for (Drawable e : elements) {
                if (e instanceof RenderCommon.Item) list.add((RenderCommon.Item) e);
            }
        }
        return list;
    }

    /**
     * @since 1.2.3
     * @see IDraw2D#getImages()
     */
    @Override
    public List<RenderCommon.Image> getImages() {
        List<RenderCommon.Image> list = new LinkedList<>();
        synchronized (elements) {
            for (Drawable e : elements) {
                if (e instanceof RenderCommon.Image) list.add((RenderCommon.Image) e);
            }
        }
        return list;
    }
    
    @Override
    public List<Drawable> getElements() {
        return ImmutableList.copyOf(elements);
    }
    
    @Override
    public Draw2D removeElement(Drawable e) {
        synchronized (elements) {
            elements.remove(e);
        }
        return this;
    }
    
    @Override
    public Drawable reAddElement(Drawable e) {
        synchronized (elements) {
            elements.add(e);
        }
        return e;
    }
    
    
    /**
     * @since 1.0.5
     * @see IDraw2D#addText(String, int, int, int, boolean)
     */
    @Override
    public RenderCommon.Text addText(String text, int x, int y, int color, boolean shadow) {
        return addText(text, x, y, color, shadow, 1, 0);
        
    }

    /**
     * @since 1.2.6
     * @see IDraw2D#addText(String, int, int, int, boolean, double, float)
     */
    @Override
    public RenderCommon.Text addText(String text, int x, int y, int color, boolean shadow, double scale, float rotation) {
        RenderCommon.Text t = new RenderCommon.Text(text, x, y, color, shadow, scale, rotation);
        synchronized (elements) {
            elements.add(t);
        }
        return t;
        
    }
    

    @Override
    public RenderCommon.Text addText(TextHelper text, int x, int y, int color, boolean shadow) {
        return addText(text, x, y, color, shadow, 1, 0);
    }

    @Override
    public RenderCommon.Text addText(TextHelper text, int x, int y, int color, boolean shadow, double scale, float rotation) {
        RenderCommon.Text t = new RenderCommon.Text(text, x, y, color, shadow, scale, rotation);
        synchronized (elements) {
            elements.add(t);
        }
        return t;
    }

    /**
     * @since 1.0.5
     * @see IDraw2D#removeText(xyz.wagyourtail.jsmacros.client.api.sharedclasses.RenderCommon.Text)
     */
    @Override
    public Draw2D removeText(RenderCommon.Text t) {
        synchronized (elements) {
            elements.remove(t);
        }
        return this;
    }

    /**
     * @since 1.2.3
     * @see IDraw2D#addImage(int, int, int, int, String, int, int, int, int, int, int)
     */
    @Override
    public RenderCommon.Image addImage(int x, int y, int width, int height, String id, int imageX, int imageY, int regionWidth, int regionHeight, int textureWidth, int textureHeight) {
        return addImage(x, y, width, height, id, imageX, imageY, regionWidth, regionHeight, textureWidth, textureHeight, 0);
    }

    /**
     * @since 1.2.6
     * @see IDraw2D#addImage(int, int, int, int, String, int, int, int, int, int, int, float)
     */
    @Override
    public RenderCommon.Image addImage(int x, int y, int width, int height, String id, int imageX, int imageY, int regionWidth, int regionHeight, int textureWidth, int textureHeight, float rotation) {
        RenderCommon.Image i = new RenderCommon.Image(x, y, width, height, id, imageX, imageY, regionWidth, regionHeight, textureWidth, textureHeight, rotation);
        synchronized (elements) {
            elements.add(i);
        }
        return i;
    }

    /**
     * @since 1.2.3
     * @see IDraw2D#removeImage(xyz.wagyourtail.jsmacros.client.api.sharedclasses.RenderCommon.Image)
     */
    @Override
    public Draw2D removeImage(RenderCommon.Image i) {
        synchronized (elements) {
            elements.remove(i);
        }
        return this;
    }

    /**
     * @since 1.0.5
     * @see IDraw2D#addRect(int, int, int, int, int)
     */
    @Override
    public RenderCommon.Rect addRect(int x1, int y1, int x2, int y2, int color) {
        RenderCommon.Rect r = new RenderCommon.Rect(x1, y1, x2, y2, color, 0F);
        synchronized (elements) {
            elements.add(r);
        }
        return r;
    }

    /**
     * @since 1.1.8
     * @see IDraw2D#addRect(int, int, int, int, int, int)
     */
    @Override
    public RenderCommon.Rect addRect(int x1, int y1, int x2, int y2, int color, int alpha) {
        return addRect(x1, y1, x2, y2, color, alpha, 0);
    }

    /**
     * @since 1.2.6
     * @see IDraw2D#addRect(int, int, int, int, int, int, float)
     */
    @Override
    public RenderCommon.Rect addRect(int x1, int y1, int x2, int y2, int color, int alpha, float rotation) {
        RenderCommon.Rect r = new RenderCommon.Rect(x1, y1, x2, y2, color, alpha, rotation);
        synchronized (elements) {
            elements.add(r);
        }
        return r;
    }

    /**
     * @since 1.0.5
     * @see IDraw2D#removeRect(xyz.wagyourtail.jsmacros.client.api.sharedclasses.RenderCommon.Rect)
     */
    @Override
    public Draw2D removeRect(RenderCommon.Rect r) {
        synchronized (elements) {
            elements.remove(r);
        }
        return this;
    }

    /**
     * @since 1.0.5
     * @see IDraw2D#addItem(int, int, String)
     */
    @Override
    public RenderCommon.Item addItem(int x, int y, String id) {
        return addItem(x, y, id, true);
    }
    
    /**
     * @since 1.2.0
     * @see IDraw2D#addItem(int, int, String, boolean)
     */
    @Override
    public RenderCommon.Item addItem(int x, int y, String id, boolean overlay) {
        return addItem(x, y, id, overlay, 1, 0);
    }

    /**
     * @since 1.2.0
     * @see IDraw2D#addItem(int, int, String, boolean, double, float)
     */
    @Override
    public RenderCommon.Item addItem(int x, int y, String id, boolean overlay, double scale, float rotation) {
        RenderCommon.Item i = new RenderCommon.Item(x, y, id, overlay, scale, rotation);
        synchronized (elements) {
            elements.add(i);
        }
        return i;
    }

    /**
     * @since 1.0.5
     * @see IDraw2D#addItem(int, int, ItemStackHelper)
     */
    public RenderCommon.Item addItem(int x, int y, ItemStackHelper Item) {
        return addItem(x, y, Item, true);
    }
    
    /**
     * @since 1.2.0
     * @see IDraw2D#addItem(int, int, ItemStackHelper, boolean)
     */
    @Override
    public RenderCommon.Item addItem(int x, int y, ItemStackHelper Item, boolean overlay) {
        return addItem(x, y, Item, overlay, 1, 0);
    }

    /**
     * @since 1.2.6
     * @see IDraw2D#addItem(int, int, ItemStackHelper, boolean, double, float)
     */
    @Override
    public RenderCommon.Item addItem(int x, int y, ItemStackHelper Item, boolean overlay, double scale, float rotation) {
        RenderCommon.Item i = new RenderCommon.Item(x, y, Item, overlay, scale, rotation);
        synchronized (elements) {
            elements.add(i);
        }
        return i;
    }

    /**
     * @since 1.0.5
     * @see IDraw2D#removeItem(xyz.wagyourtail.jsmacros.client.api.sharedclasses.RenderCommon.Item)
     */
    @Override
    public Draw2D removeItem(RenderCommon.Item i) {
        synchronized (elements) {
            elements.remove(i);
        }
        return this;
    }

    @Override
    public void init() {
        synchronized (elements) {
            elements.clear();
        }
        if (onInit != null) {
            try {
                onInit.accept(this);
            } catch(Exception e) {
                e.printStackTrace();
                try {
                    if (catchInit != null) catchInit.accept(e.toString());
                } catch (Exception f) {
                    f.printStackTrace();
                }
            }
        }
    }

    @Override
    public void render(MatrixStack matrixStack) {
        if (matrixStack == null) return;
        
        RenderSystem.pushMatrix();
        synchronized (elements) {
            for (Drawable e : elements) {
                e.render(matrixStack, 0, 0, 0);
            }
        }
        RenderSystem.popMatrix();
    }

    /**
     * @since 1.2.7
     * @see IDraw2D#setOnInit(MethodWrapper)
     * @param onInit calls your method as a {@link java.util.function.Consumer Consumer}&lt;{@link Draw2D}&gt;
     */
    @Override
    public Draw2D setOnInit(MethodWrapper<Draw2D, Object, Object> onInit) {
        this.onInit = onInit;
        return this;
    }

    
    /**
     * @since 1.2.7
     * @see IDraw2D#setOnFailInit(MethodWrapper)
     * @param catchInit calls your method as a {@link java.util.function.Consumer Consumer}&lt;{@link java.lang.String String}&gt;
     */
    @Override
    public Draw2D setOnFailInit(MethodWrapper<String, Object, Object> catchInit) {
        this.catchInit = catchInit;
        return this;
    }
}