package org.silvercatcher.reforged.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityBulletMusket extends AReforgedThrowable {
	
	public EntityBulletMusket(World worldIn) {
		
		super(worldIn, "musket");
	}
	
	public EntityBulletMusket(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
		
		super(worldIn, throwerIn, stack, "musket");
	}
	
	@Override
	protected boolean onEntityHit(Entity entity) {
		entity.attackEntityFrom(causeImpactDamage(entity, getThrower()), getImpactDamage(entity));
		return true;
	}
	
	@Override
	protected float getImpactDamage(Entity target) {

		return 10f;
	}
}
