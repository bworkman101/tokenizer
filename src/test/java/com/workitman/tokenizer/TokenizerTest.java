package com.workitman.tokenizer;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeTrue;

public class TokenizerTest {

    private String masterFile;

    @Before
    public void setupFiles() throws Exception {
        String filePath1 = writeFile("test_file_mapping1", "This is only a test. A *very* short test!\n");
        String filePath2 = writeFile("test_file_mapping2", "Hello World!");
        masterFile = writeFile("master_file", filePath1 + "\n" + filePath2 + "\n");
    }

    private String writeFile(String fileName, String content) throws IOException {
        File tmpFile = File.createTempFile(fileName, "map");
        assumeTrue(tmpFile.canRead());
        assumeTrue(tmpFile.canWrite());

        BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile.getAbsoluteFile()));
        writer.write(content);
        writer.flush();
        writer.close();

        return tmpFile.getAbsolutePath();
    }

    @Test
    public void tokenizeFiles() {
        Tokenizer tokenizer = new Tokenizer();
        Map<String, Integer> tokenized = tokenizer.tokenizeFiles(masterFile);

        assertThat(tokenized.size(), equalTo(9));
        assertThat(tokenized.get("a"), equalTo(2));
        assertThat(tokenized.get("test"), equalTo(2));
        assertThat(tokenized.get("is"), equalTo(1));
        assertThat(tokenized.get("only"), equalTo(1));
        assertThat(tokenized.get("short"), equalTo(1));
        assertThat(tokenized.get("this"), equalTo(1));
        assertThat(tokenized.get("very"), equalTo(1));
        assertThat(tokenized.get("hello"), equalTo(1));
        assertThat(tokenized.get("world"), equalTo(1));
    }

}