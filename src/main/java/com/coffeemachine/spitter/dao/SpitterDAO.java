package com.coffeemachine.spitter.dao;

import com.coffeemachine.spitter.domain.Spitter;

public interface SpitterDAO {
	
	public void addSpitter(Spitter spitter);
	
	public Spitter getSpitterById(long id);

}
