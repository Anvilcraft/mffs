package mffs.item.module.interdiction;

import mffs.ModularForceFieldSystem;
import net.minecraft.entity.INpc;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.EntityLivingBase;
import mffs.api.security.IInterdictionMatrix;

public class ItemModuleAntiHostile extends ItemModuleInterdictionMatrix
{
    public ItemModuleAntiHostile() {
        super("moduleAntiHostile");
    }
    
    @Override
    public boolean onDefend(final IInterdictionMatrix interdictionMatrix, final EntityLivingBase entityLiving) {
        if (entityLiving instanceof IMob && !(entityLiving instanceof INpc)) {
            entityLiving.attackEntityFrom(ModularForceFieldSystem.damagefieldShock, 20);
        }
        return false;
    }
}
