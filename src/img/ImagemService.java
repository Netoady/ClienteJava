package src.img;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class ImagemService {

    public String converterParaBase64(String caminhoArquivo) throws IOException {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            throw new IOException("Arquivo não encontrado: " + caminhoArquivo);
        }
        byte[] bytes = Files.readAllBytes(arquivo.toPath());
        return Base64.getEncoder().encodeToString(bytes);
    }

    public void salvarBase64(String base64Str, String caminhoSaida) throws IOException {
        byte[] bytes = Base64.getDecoder().decode(base64Str);
        File arquivoSaida = new File(caminhoSaida);

        try (FileOutputStream fos = new FileOutputStream(arquivoSaida)) {
            fos.write(bytes);
        }

        System.out.println("Imagem salva em: " + arquivoSaida.getAbsolutePath());

        // Abre automaticamente o arquivo gerado no Google Chrome
        try {
            Runtime.getRuntime().exec(new String[] { "google-chrome", arquivoSaida.getAbsolutePath() });
            System.out.println("Imagem aberta no Google Chrome.");
        } catch (IOException e) {
            System.out.println("Não foi possível abrir o Chrome automaticamente: " + e.getMessage());
        }
    }
}