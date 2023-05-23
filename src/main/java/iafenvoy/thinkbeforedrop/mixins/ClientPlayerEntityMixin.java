package iafenvoy.thinkbeforedrop.mixin;

import iafenvoy.thinkbeforedrop.DropManager;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    @Shadow
    @Final
    protected MinecraftClient client;

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "dropSelectedItem(Z)Z", at = @At("HEAD"), cancellable = true)
    public void beforeDropItem(boolean dropEntireStack, CallbackInfoReturnable<Boolean> cir) {
        if (!DropManager.shouldThrow(this.getInventory().getStack(this.getInventory().selectedSlot), this.getInventory().selectedSlot)) {
            assert client.player != null;
            client.player.sendMessage(DropManager.getWarningText(), true);
            cir.setReturnValue(false);
        }
    }
}
