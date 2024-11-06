package com.daesoo.dmotools.seal.dto.response;

import lombok.Builder;
import lombok.Getter;
import com.daesoo.dmotools.common.entity.Character;

@Getter
@Builder
public class CharacterResponseDto {

	private Long id;
	
	private String name;
	
	public static CharacterResponseDto of(Character character) {
		return CharacterResponseDto.builder()
				.id(character.getId())
				.name(character.getName())
				.build();
	}
	
}
