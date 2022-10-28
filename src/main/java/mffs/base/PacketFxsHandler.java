package mffs.base;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mffs.base.TileEntityBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketFxsHandler implements IMessageHandler<PacketFxs, IMessage> {

    @Override
    public IMessage onMessage(PacketFxs message, MessageContext ctx) {
        World world = FMLClientHandler.instance().getWorldClient();

        TileEntity te = message.pos.getTileEntity(world);
        if (te instanceof TileEntityBase) {
            ((TileEntityBase) te).onFxsPacket(message.data);
        }

        return null;
    }
}
