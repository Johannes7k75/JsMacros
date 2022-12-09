package xyz.wagyourtail.jsmacros.client.api.helpers.entity.specialized.other;

import net.minecraft.entity.FallingBlockEntity;

import xyz.wagyourtail.jsmacros.client.api.helpers.BlockPosHelper;
import xyz.wagyourtail.jsmacros.client.api.helpers.BlockStateHelper;
import xyz.wagyourtail.jsmacros.client.api.helpers.EntityHelper;

/**
 * @author Etheradon
 * @since 1.8.4
 */
@SuppressWarnings("unused")
public class FallingBlockEntityHelper extends EntityHelper<FallingBlockEntity> {

    public FallingBlockEntityHelper(FallingBlockEntity base) {
        super(base);
    }

    /**
     * @return the block position this block is falling from.
     *
     * @since 1.8.4
     */
    public BlockPosHelper getOriginBlockPos() {
        return new BlockPosHelper(base.getFallingBlockPos());
    }

    /**
     * @return the block state of this falling block.
     *
     * @since 1.8.4
     */
    public BlockStateHelper getBlockState() {
        return new BlockStateHelper(base.getBlockState());
    }

}
