package xyz.wagyourtail.jsmacros.client.api.helpers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import xyz.wagyourtail.jsmacros.client.api.classes.TextBuilder;
import xyz.wagyourtail.jsmacros.client.api.sharedclasses.RenderCommon;
import xyz.wagyourtail.jsmacros.core.Core;
import xyz.wagyourtail.jsmacros.core.helpers.BaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

/**
 * @author Wagyourtail
 * @since 1.0.5
 */
@SuppressWarnings("unused")
public class ClickableWidgetHelper<B extends ClickableWidgetHelper<B, T>, T extends ClickableWidget> extends BaseHelper<T> implements RenderCommon.RenderElement, RenderCommon.Alignable<B> {
    public int zIndex;
    public List<Text> tooltips;

    public ClickableWidgetHelper(T btn) {
        this(btn, 0);
    }

    public ClickableWidgetHelper(T btn, int zIndex) {
        super(btn);
        this.zIndex = zIndex;
        this.tooltips = new ArrayList<>();
    }
    
    /**
     * @since 1.0.5
     * @return the {@code x} coordinate of the button.
     */
    public int getX() {
        return base.x;
    }

    /**
     * @since 1.0.5
     * @return the {@code y} coordinate of the button.
     */
    public int getY() {
        return base.y;
    }
    
    /**
     * Set the button position.
     * 
     * @since 1.0.5
     * 
     * @param x
     * @param y
     * @return
     */
    public B setPos(int x, int y) {
        base.x = x;
        base.y = y;
        return (B) this;
    }
    
    /**
     * @since 1.0.5
     * 
     * @return
     */
    public int getWidth() {
        return base.getWidth();
    }

    /**
     * @return the height of the button.
     *
     * @since 1.8.4
     */
    public int getHeight() {
        return base.getHeight();
    }
    
    /**
     * change the text.
     *
     * @since 1.0.5, renamed from {@code setText} in 1.3.1
     * @deprecated only deprecated in buttonWidgetHelper for confusing name.
     *
     * @param label
     * @return
     */
     @Deprecated
    public B setLabel(String label) {
        base.setMessage(Text.literal(label));
        return (B) this;
    }
    
    /**
     * change the text.
     *
     * @since 1.3.1
     *
     * @param helper
     *
     * @return
     */
    public B setLabel(TextHelper helper) {
        base.setMessage(helper.getRaw());
        return (B) this;
    }
    
    /**
     * @since 1.2.3, renamed fro {@code getText} in 1.3.1
     * 
     * @return current button text.
     */
    public TextHelper getLabel() {
        return new TextHelper(base.getMessage());
    }
    
    /**
     * @since 1.0.5
     * 
     * @return button clickable state.
     */
    public boolean getActive() {
        return base.active;
    }
    
    /**
     * set the button clickable state.
     * 
     * @since 1.0.5
     * 
     * @param t
     * @return
     */
    public B setActive(boolean t) {
        base.active = t;
        return (B) this;
    }
    
    /**
     * set the button width.
     * 
     * @since 1.0.5
     * 
     * @param width
     * @return
     */
    public B setWidth(int width) {
        base.setWidth(width);
        return (B) this;
    }
    
    /**
     * clicks button
     * @since 1.3.1
     */
    public B click() throws InterruptedException {
        click(true);
        return (B) this;
    }
    
    /**
     * clicks button
     *
     * @param await should wait for button to finish clicking.
     * @since 1.3.1
     */
    public B click(boolean await) throws InterruptedException {
        if (Core.getInstance().profile.checkJoinedThreadStack()) {
            base.mouseClicked(base.x, base.y, 0);
            base.mouseReleased(base.x, base.y, 0);
        } else {
            final Semaphore waiter = new Semaphore(await ? 0 : 1);
            MinecraftClient.getInstance().execute(() -> {
                base.mouseClicked(base.x, base.y, 0);
                base.mouseReleased(base.x, base.y, 0);
                waiter.release();
            });
            waiter.acquire();
        }
        return (B) this;
    }

    /**
     * @param tooltips the tooltips to set
     * @return self for chaining.
     *
     * @since 1.8.4
     */
    public B setTooltip(Object... tooltips) {
        this.tooltips = new ArrayList<>();
        for (Object text : tooltips) {
            addTooltip(text);
        }
        return (B) this;
    }

    /**
     * @param tooltip the tooltips to add
     * @return self for chaining.
     *
     * @since 1.8.4
     */
    public B addTooltip(Object tooltip) {
        if (tooltip instanceof TextBuilder) {
            tooltips.add(((TextBuilder) tooltip).build().getRaw());
        } else if (tooltip instanceof TextHelper) {
            tooltips.add(((TextHelper) tooltip).getRaw());
        } else if (tooltip instanceof String) {
            tooltips.add(Text.literal((String) tooltip));
        } else {
            tooltips.add(Text.literal(tooltip.toString()));
        }
        return (B) this;
    }

    /**
     * @param index the index of the tooltip to remove
     * @return {@code true} if the tooltip was removed successfully, {@code false} otherwise.
     *
     * @since 1.8.4
     */
    public boolean removeTooltip(int index) {
        if (index >= 0 && index < tooltips.size()) {
            tooltips.remove(index);
            return true;
        }
        return false;
    }

    /**
     * @param tooltip the tooltip to remove
     * @return {@code true} if the tooltip was removed successfully, {@code false} otherwise.
     *
     * @since 1.8.4
     */
    public boolean removeTooltip(TextHelper tooltip) {
        return tooltips.remove(tooltip.getRaw());
    }

    /**
     * @return a copy of the tooltips.
     *
     * @since 1.8.4
     */
    public List<TextHelper> getTooltips() {
        return tooltips.stream().map(TextHelper::new).collect(Collectors.toList());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        base.render(matrices, mouseX, mouseY, delta);
        if (base.isMouseOver(mouseX, mouseY) && tooltips.size() > 0) {
            MinecraftClient.getInstance().currentScreen.renderTooltip(matrices, tooltips, mouseX, mouseY);
        }
    }
    
    @Override
    public int getZIndex() {
        return zIndex;
    }

    @Override
    public String toString() {
        return String.format("ButtonWidgetHelper:{\"message\": \"%s\"}", base.getMessage().getString());
    }

    @Override
    public int getScaledWidth() {
        return getWidth();
    }

    @Override
    public int getParentWidth() {
        return MinecraftClient.getInstance().currentScreen.width;
    }

    @Override
    public int getScaledHeight() {
        return getHeight();
    }

    @Override
    public int getParentHeight() {
        return MinecraftClient.getInstance().currentScreen.height;
    }

    @Override
    public int getScaledLeft() {
        return getX();
    }

    @Override
    public int getScaledTop() {
        return getY();
    }

    @Override
    public B moveTo(int x, int y) {
        return setPos(x, y);
    }
    
}