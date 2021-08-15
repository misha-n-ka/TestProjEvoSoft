package com.testprojevosoft.data

import android.util.Log
import kotlinx.coroutines.delay

class Database {
    companion object {

        private var requestCount = 0

        val phoneNumbers: List<String> = listOf(
            "+79998887766"
        )

        val smsCodes: List<String> = listOf(
            "1111"
        )

        var images: List<String> = listOf(
            "https://pixabay.com/get/g7b6ab046bfb0be81e5934b51e45f09ed27b2695e005dfe9ac92450aac1fd2d0ca0e3c1faaf7444ed2ef5965d3d160af4_1280.jpg",
            "https://pixabay.com/get/gfd548cb23335558835fc78ed06ed1acabe145c3df9b45d094ccbcc7933b1d1ce0727bf5894ffc4b683981ccda6894687_1280.jpg",
            "https://pixabay.com/get/g59e167d604f7e28aef215ec8c296f6fc825d56439e02b2660b7825d571bbb403681dca06110baf20fda54e41ae8ed0f1_1280.jpg",
            "https://pixabay.com/get/g4f82df5e2dbd7f7f417d7512474537adc7d8b0fb98d1e1003e0e1668790a287088d7a9068ffd1222be6dd6bdc48c442c_1280.jpg",
            "https://pixabay.com/get/g76f327d60fcff6408b532dc8e46dc91b011526d945d86be44cf72b6a69dec13dfcdce4054fca0bbac5f9e838cf78676e_1280.png",
            "https://pixabay.com/get/g65235562d49ec8590b15cfc364d7392dbd1e98ae5d421681209155001a2779746f066aece25d9daa6c95993b31a9e1a5_1280.jpg",
            "https://pixabay.com/get/g21fdf7802e20135634999bb3a5148a54f462ae6a9ca5e14d6590f1f19e5f8eff71bc3f2a4b5dba01ffdcb11be6a7645d_1280.png",
            "https://pixabay.com/get/g8edcc9ceb72c73e63fad1c9548d9eb658b2b01663b0c2ea95e384c240fa67fde302276df056d041db569eb10b877db71_1280.jpg",
            "https://pixabay.com/get/g20f7a33f1604ea35627d3840e037e2ee22932a6ae6b37b500646e6b3c1770a78c17e4af4b78b98f55e44b5ff017f756c_1280.jpg",
            "https://pixabay.com/get/g51619c80a3c90c01484230b3d89b2c79531c989f3f36b00beba3d0145b3d99a3b2199b87f596d56c781f0479478d3641_1280.jpg",
            "https://pixabay.com/get/gf689fd66fe4ff1d860053ef556bfaef71451eab0ca2d636ade53cbbc9560026d6dfb4c9a9d6868004acd731647d4069e_1280.jpg",
            "https://pixabay.com/get/g29cd5a89e596497aced94be28fe8a49dd69395c6d1a40aec676b50c7dc0638576b1448ae030e75ce78a755073baf46fc_1280.jpg",
            "https://pixabay.com/get/g722761647b10cc2312611acf28e4bfe3add2c2deca67bb46c486cd844a11b8dcf28fad420770a4ace68a8d11333c5d32_1280.jpg",
            "https://pixabay.com/get/gacf1bc1fe1460f982d57b72017db8337632c83ec93ba40ee4bf9f49db2d3255617035e2039ce76c78e9fff8568d2749f_1280.jpg",
            "https://pixabay.com/get/gc515e3de810bc6a2767c98d8e7fb24e4b9ecc8d9b6f5a895388d576dcb338cf8ab9d067306b8960e7c60fd56b630ef68_1280.jpg",
            "https://pixabay.com/get/g71e563897677009480500d349a1889ef66d2afb41fb163441929d87064e410020c780cec00d6a23dc5a43f936b804d30_1280.jpg",
            "https://pixabay.com/get/g49a24f46910cab0eaa3372ec0647ea124c04ccfcc58ab366ec176cc8abe94400b229b7ffcc8578d62cf07f620d71ab43_1280.jpg",
            "https://pixabay.com/get/g35a764a7cc2100ef178c6bb24a4b2c2fd7e46b1cd05028b06c827aaf968529a46cfb7dd023e8a94524c43cdb75087f2e_1280.jpg",
            "https://pixabay.com/get/g74572854b7f59f8bd4fa0c127f9be51f4841c7bcd294f46fa43ddcf73a25af385add01e568bce9b93d9c1d38ef3405b1_1280.jpg",
            "https://pixabay.com/get/g2b4bb93fc4e8da4fbe7d872173b3b333b920453758d37263d5c6e5a6972578db7789fcd4896efa0fdaf24d1aa42981b0_1280.jpg",
            "https://pixabay.com/get/g4751538e543e213316f64ec3876f470926f454e61cdafcb8ebcfa645153f106e2b0647c782be5e88b62ac7dfb38992cd_1280.jpg",
            "https://pixabay.com/get/g3b643b60faa91311f4a9752c70849a6f2f4fdf44e34dae4c820f83e27f076019c8a44d8a52186ff71f25135ee19db68b_1280.jpg",
            "https://pixabay.com/get/ga61f48009e29f5784ce7bbc95fe9747160a1fdba87d07c5ee66baa1cf4cc63bd8ffb21542e74cf390982c404192e739b_1280.jpg",
            "https://pixabay.com/get/g424ef9062e191e2050797a669b944bbbbc1545beacbfff35d1c6d1adaf3ec34a31058a52765e4987d09cfcbc9e88afb2_1280.jpg",
            "https://pixabay.com/get/g7cf6d2a2a13464a2e7e828a2ccc9d86331dc1b1f563d882e5fe1499b2e5fb104e9cb3d38d724f8e05d72582667280233_1280.jpg",
            "https://pixabay.com/get/gef7f6d5cd124e732164c54ebefa83e539f509b93a9e4ab354e8ff64fec58f3131eb96ed9d488eec77dac95691c064937_1280.jpg",
            "https://pixabay.com/get/g001d455904a101fa9c161e1c1f88d04cb5fc5005b3ed88b5b8c0644af8d0c4a4bc98ef6a1a732b7141d8da8f1ac926a2_1280.jpg",
            "https://pixabay.com/get/gb280954bcf68fa97030f2e9ef15305e2aa881accabc6695ee932de86a24ba95d56ae039637707330a127588b04778dcf_1280.jpg",
            "https://pixabay.com/get/g966c6eccd9fc84bd2be2a81856a778769827137e9fe90adbc7f268150c4e2d8bd5804e16e811ea3a2fb4c0aa49673ad7_1280.jpg",
            "https://pixabay.com/get/g34a9147ead6675a9b38d49585267e62425cb6ee33aff63c85e73b7c9296e0ae408b75b620447b7983d086ecd89480e49_1280.jpg",
            "https://pixabay.com/get/g13df87b584e565e50763694024ff3da5df2e25f299c7a22d7c7e1d4a105c4bef1a8d50d58a1a42ba948533291b23f880_1280.jpg",
        )

        suspend fun requestVerificationCode(number: String): Boolean {
            delay(2000L)
            return isNumberInBase(number)
        }

        // check if number is in database
        fun isNumberInBase(number: String): Boolean {
            return phoneNumbers.firstOrNull { it == number } != null
        }

        suspend fun getNextImages(numLoadItems: Int): List<String> {
            delay(2000L)
            // start index for buffered list images
            val minRange = numLoadItems * requestCount
            requestCount++
            // las index for buffered list image
            var maxRange = numLoadItems * requestCount
            // lf buffered list of numLoadItems bigger than reminder of base image list size
            if (maxRange > images.size) {
                maxRange = images.size
                requestCount = 0
            }
            //init empty buffered list
            val resultList = mutableListOf<String>()

            // filling buffered list
            if (maxRange <= images.size) {
                for (i in minRange until maxRange) {
                    resultList.add(images[i])
                }
            }
            return resultList.toList()
        }
    }
}