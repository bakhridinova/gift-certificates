package com.epam.esm.util.mapper;

import com.epam.esm.config.ServiceTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.epam.esm.util.ServiceTestEntityHolder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
class TagMapperTest {
    private final TagMapper tagMapper;

    @Autowired
    public TagMapperTest(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Test
    public void shouldMapTagsCorrectlyTest() {
        assertEquals(tag, tagMapper.toTag(tagDTO));
        assertEquals(tagDTO, tagMapper.toTagDTO(tag));
    }

    @Test
    public void shouldReturnNullIfNullPassedTest() {
        assertNull(tagMapper.toTag(null));
        assertNull(tagMapper.toTagDTO(null));
    }

    @Test
    public void shouldReturnNullObjectIfNullObjectPassedTest() {
        assertEquals(nullTag, tagMapper.toTag(nullTagDTO));
        assertEquals(nullTagDTO, tagMapper.toTagDTO(nullTag));
    }
}