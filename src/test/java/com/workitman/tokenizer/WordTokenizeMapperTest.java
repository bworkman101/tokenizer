package com.workitman.tokenizer;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeTrue;

public class WordTokenizeMapperTest {

    @Test
    public void map() throws Exception {

        File tmpFile = File.createTempFile("test_file_mapping", "map");
        assumeTrue(tmpFile.canRead());
        assumeTrue(tmpFile.canWrite());

        BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile.getAbsoluteFile()));
        writer.write("This is only a test. A *very* short test!\n");
        writer.write("Hello World!");
        writer.flush();
        writer.close();

        WordTokenizeMapper wordTokenizeMapper = new WordTokenizeMapper();
        Map<String, Integer> map = wordTokenizeMapper.map(tmpFile.getAbsolutePath());

        assertThat(map.size(), equalTo(9));
        assertThat(map.get("a"), equalTo(2));
        assertThat(map.get("test"), equalTo(2));
        assertThat(map.get("is"), equalTo(1));
        assertThat(map.get("only"), equalTo(1));
        assertThat(map.get("short"), equalTo(1));
        assertThat(map.get("this"), equalTo(1));
        assertThat(map.get("very"), equalTo(1));
        assertThat(map.get("hello"), equalTo(1));
        assertThat(map.get("world"), equalTo(1));
    }

}