package mffs.base;

import calclavia.lib.IUniversalEnergyTile;
import java.util.EnumSet;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.core.electricity.ElectricityNetworkHelper;
import universalelectricity.core.electricity.ElectricityPack;

public abstract class TileEntityUniversalEnergy
    extends TileEntityModuleAcceptor implements IUniversalEnergyTile {
  public double prevWatts;
  public double wattsReceived;

  public TileEntityUniversalEnergy() { this.wattsReceived = 0.0; }

  @Override
  public void updateEntity() {
    super.updateEntity();
    this.prevWatts = this.wattsReceived;
    if (!this.worldObj.isRemote) {
      if (!this.isDisabled()) {
        final ElectricityPack electricityPack =
            ElectricityNetworkHelper.consumeFromMultipleSides(
                this, this.getConsumingSides(), this.getRequest());
        this.onReceive(electricityPack);
      } else {
        ElectricityNetworkHelper.consumeFromMultipleSides(
            this, new ElectricityPack());
      }
    }
  }

  protected EnumSet<ForgeDirection> getConsumingSides() {
    return ElectricityNetworkHelper.getDirections(this);
  }

  public ElectricityPack getRequest() { return new ElectricityPack(); }

  public void onReceive(final ElectricityPack electricityPack) {
    if (UniversalElectricity.isVoltageSensitive &&
        electricityPack.voltage > this.getVoltage()) {
      return;
    }
    this.wattsReceived = Math.min(
        this.wattsReceived + electricityPack.getWatts(), this.getWattBuffer());
  }

  public double getWattBuffer() { return this.getRequest().getWatts() * 2.0; }

  @Override
  public double getVoltage() {
    return 120.0;
  }

  public ElectricityPack produce(double watts) {
    ElectricityPack pack =
        new ElectricityPack(watts / this.getVoltage(), this.getVoltage());
    ElectricityPack remaining =
        ElectricityNetworkHelper.produceFromMultipleSides(this, pack);

    return remaining;
  }
}
