// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static void main(String[] args) {
        //Caesar
        String plainText = "Nguyen @Dac, / Tony Cuong 1230.";
        int shift = 5;
        System.out.println("Text: "+plainText+" - Key: "+shift);
        String caesarEncrypt = caesarEncrypt(plainText, shift);
        System.out.println("Encrypt: "+caesarEncrypt);
        String caesarDecrypt = caesarDecrypt(caesarEncrypt, shift);
        System.out.println("Decrypt: "+caesarDecrypt);

        System.out.println("---------------------");

        //Simple Substitution Cipher
        String keyCipher = generateRandomKey();
        System.out.println("Text: "+plainText);
        System.out.println("Key Cipher: "+keyCipher);
        String encrypt = encrypt(plainText, keyCipher);
        System.out.println("Encrypt: "+encrypt);
        String decrypt = decrypt(encrypt, keyCipher);
        System.out.println("Decrypt: "+decrypt);

        System.out.println("---------------------");

        //VietNamese
        String text = "”Tất cả nick ảo, nick clone k rõ danh tính, nick mới tham gia chợ không được tham gia bid.”\n" +
                "------------------------------\n" +
                "Bid dọn tủuuu\n" +
                "Sản phẩm: 90sw Cherry Blossom và 90sw Geon Black\n" +
                "Tình trạng: \n" +
                "-Cherry Blossom đã lube và thay lò xo payson 2stage 62g bởi chủ cũ, 84 rã hàn cắm hs bth, 6 chưa dùng. \n" +
                "-Geon Black đã lube và thay lò xo tx long 60g bởi c NTN, 84 rã hàn cắm hs bth, 6 chưa dùng. \n" +
                "---------------------------------\n" +
                "Giá khởi điểm: 0 đồng\n" +
                "Bước giá: bội 10k, nhảy tự do\n" +
                "Giá mua ngay: 1200k (free ship)\n" +
                "End : 22h30 ngày 30/09/2023 (22h31 không tính)\n" +
                "---------------------------------\n" +
                "Winner vui lòng thanh toán trong 24h kể từ khi win bid và phí ship nếu có.\n" +
                "Nếu có tranh chấp nhờ admin xử lý.";
        int key = 24;
        System.out.println("TextVie: "+text);
        System.out.println("Key: "+key);
        String encryptVie = encryptVietnamese(key, text);
        System.out.println("EncryptVie: "+encryptVie);
        String decryptVie = decryptVietnamese(key, encryptVie);
        System.out.println("DecryptVie: "+decryptVie);
    }

    //Encrypt
    public static String caesarEncrypt(String plainText, int shift){
        StringBuilder encryptedText = new StringBuilder();
        for(char character : plainText.toCharArray()){
            if(Character.isLetter(character)){
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                //a -> z have 26 characters
                char encryptChar = (char) ((character - base + shift) % 26 + base);
                encryptedText.append(encryptChar);
            }
            else encryptedText.append(character);
        }
        return encryptedText.toString();
    }
    //Decrypt
    public static String caesarDecrypt(String cipherText, int shift){
        StringBuilder decryptText = new StringBuilder();
        for(char character : cipherText.toCharArray()){
            if(Character.isLetter(character)){
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                //a -> z have 26 characters
                char decryptChar = (char) ((character - base - shift + 26) % 26 + base);
                decryptText.append(decryptChar);
            }
            else decryptText.append(character);
        }
        return decryptText.toString();
    }

    //random Key
    public static String generateRandomKey(){
        StringBuilder keyBuilder = new StringBuilder(ALPHABET);
        for(int i = 0; i < ALPHABET.length(); i++){
            int randomIndex = (int) (Math.random() * keyBuilder.length());
            char temp = keyBuilder.charAt(i);
            keyBuilder.setCharAt(i, keyBuilder.charAt(randomIndex));
            keyBuilder.setCharAt(randomIndex, temp);
        }
        return keyBuilder.toString();
    }

    //Encrypt Simple Substitution Cipher
    public static String encrypt(String plainText, String key){
        StringBuilder cipherText = new StringBuilder();
        for(char character : plainText.toCharArray()){
            if(Character.isLetter(character)){
                char originalChar = Character.toUpperCase(character);
                int index = ALPHABET.indexOf(originalChar);
                char substituteChar = key.charAt(index);
                if(Character.isUpperCase(character)){
                    cipherText.append(substituteChar);
                }
                else cipherText.append(Character.toLowerCase(substituteChar));
            }
            else cipherText.append(character);
        }
        return cipherText.toString();
    }

    //Decrypt Simple Substitution Cipher
    public static String decrypt(String cipherText, String key){
        StringBuilder plainText = new StringBuilder();
        for(char character : cipherText.toCharArray()){
            if(Character.isLetter(character)){
                char originalChar = Character.toUpperCase(character);
                int index = key.indexOf(originalChar);
                char substituteChar = ALPHABET.charAt(index);
                if(Character.isUpperCase(character)){
                    plainText.append(substituteChar);
                }
                else plainText.append(Character.toLowerCase(substituteChar));
            }
            else plainText.append(character);
        }
        return plainText.toString();
    }
    public static final String VIETNAMESE_APHABET = "aáàạảãăắằặẳẵâấầậẩẫbcdđeéèẹẻẽêếềệểễghiíìịỉĩklmnoóòọỏõôốồộổỗơớờợởỡpqrstuúùụủũưứừựửữvxyýỳỵỷỹAÁÀẠẢÃĂẮẰẶẲẴÂẤẦẬẨẪBCDĐEÉÈẸẺẼÊẾỀỆỂỄGHIÍÌỊỈĨKLMNOÓÒỌỎÕÔỐỒỘỔỖƠỚỜỢỞỠPQRSTUVXYÝỲỴỶỸ"
    //VietNamese
    public static String encryptVietnamese(int key, String data){
        StringBuilder stringBuilder = new StringBuilder();
        for(Character character : data.toCharArray()){
            int index = VIETNAMESE_APHABET.indexOf(character);
            if(index!=-1){
                stringBuilder.append(VIETNAMESE_APHABET.charAt(index+VIETNAMESE_APHABET.length()+key)%VIETNAMESE_APHABET.length());
            }
            else stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }
    public static String decryptVietnamese(int key, String data){
        return encrypt(-key, data);
    }
}