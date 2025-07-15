package com.parkinglot.singleton;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import com.parkinglot.dto.ManagerResponse;
import com.parkinglot.entity.ParkingManagerEntity;

public class SingletonModelMapper {

	private static final ModelMapper mapper = new ModelMapper();

	private SingletonModelMapper() {
	}

	static {
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		mapper.typeMap(ParkingManagerEntity.class, ManagerResponse.class).addMappings(m -> {
			m.skip(ManagerResponse::setRoles);
		});
	}

	/*public static <S,D> void skipField(Class<S> source,Class<D> dest) {
		getMapper().typeMap(source, dest).addMappings(map->{
			map.skip(dest->{
				
			});
		});
	}*/

	public static <D> D mapData(Object source, Class<D> destinationType) {
		return mapper.map(source, destinationType);
	}

	public static <T> T mapData(Object source, T destination) {
		mapper.map(source, destination);
		return destination;
	}

	public static ModelMapper getMapper() {
		return mapper;
	}
}