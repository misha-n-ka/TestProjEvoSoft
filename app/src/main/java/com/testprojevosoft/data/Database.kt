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
            "https://pixabay.com/get/geea14c2c655279eb22de4419b5e4904acced07f2f719b3b91cb3a22c22802a3e85aa8193577beacf62036869db46ad0f_1280.jpg",
            "https://pixabay.com/get/gd862fc7986fb415f2fc0ecd43670942f53f69081b366ba541dce12f57010a3a37391d77b8462cb55dbc9ba1c021a5206_1280.jpg",
            "https://pixabay.com/get/g0f4e5dcf0278142feadfcf7b95ca4a6f586025ac028a4e2c907f9381fdbaeb725959b725ca6770585baf9dc2b81a492b_1280.jpg",
            "https://pixabay.com/get/g5f69b4dfb025f827290689b24a3eeee05e82ad874958629cd2bda6659543dbe7d69741917e221fe6cef96ed0981c40e1_1280.jpg",
            "https://pixabay.com/get/g355f3ac076583e8de4ea595b5606f54979da54eccc92cc705b57d43809c0754806e0e8084b1cfe37460c1cf26c6a4fde_1280.jpg",
            "https://pixabay.com/get/g98f1166cf5e600b8ba45fcb1e628855d4501fbc3789c160e3d308976c8977904b25fce29d5690840bb3ba75e4c8113e7_1280.jpg",
            "https://pixabay.com/get/g596fe055b88c9b9ba8bc6354037b5862e922188db4fd842ebbb60c3456672de6bdc9ae600f3fd2b7eeff52d9bdc4e4f7_1280.jpg",
            "https://pixabay.com/get/gb8720bd4aae01370df5acad8ee8db430468f17970b26b8c708e4418a20e23e7f2072ccfaeca5c75f2f491c0fb6c93d56_1280.jpg",
            "https://pixabay.com/get/g97322c630c715e5402030199b938659046cd8a082a4d820a94f44bf343fee6652e65eda4c29cee3bb8b4d7a62f5d7d5e_1280.jpg",
            "https://pixabay.com/get/g838170810357ddc4ad1af5204009bbd788772a95fe758ea5b2287a8307f29de6cc0b99fa626a2b41fceba72eb284217d_1280.jpg",
            "https://pixabay.com/get/g39fa351885a159ce3fffc51e63c433289362d7e9f4b95fb93204717339e78cdf80a39df8309f40210840204102591fb0_1280.jpg",
            "https://pixabay.com/get/g65749c713ae6729d17ec22cda8f987bf5ec7db5c103d3165daa12c39f157c8c5d44e5dfa1ba2b52da1929a6a4fe203ba_1280.jpg",
            "https://pixabay.com/get/g226ade985f71e5caf304c0fa3d9c24a54b1a7b1bf9abe2f6c29a87b90d5cbd5c58ef9a95f9066bfcf637c4127ec9cf2b_1280.jpg",
            "https://pixabay.com/get/g443178d6223e31e6dd3613cb5ff384e0fca1e1ad0e3166a145821f6d3e7a4615df38c66d9debc0b02a698de57033fd34_1280.jpg",
            "https://pixabay.com/get/gf81a25035084996ff12804d7951169e82c8397c87750d12dad8d3d41734de3c39e443d111e6d03149c66a51f3c649b82_1280.png",
            "https://pixabay.com/get/g719565b1a6b1192fa1ca799e000646b602daf6f32dc19817afa065140096255fd7b97fed1ab46ec13bb1a3d5e2b65db1_1280.jpg",
            "https://pixabay.com/get/g51fc0479c11c9ada41e98e5a60bd82bae2496d15472eba420c098c2f8b33c00c0f99742cf3554832c984cfe03b4e0f43_1280.jpg",
            "https://pixabay.com/get/gd1c49511c700957ebf4a87156e4e6f00371fca25a39715507d2e132d12f94f3173737027d6ffc61f336292dd8d7bc475_1280.jpg",
            "https://pixabay.com/get/g413fb9895102db341270ecee4aed95fd3151cd34f41af9859a239f62ee048d46b886b5ed0106a432dcd9c8498b82ed88_1280.jpg",
            "https://pixabay.com/get/g438dc85fa13f122017be0b31c001f4acf532540d1d5a401b574113daa97564b63529b92ca68fd82fa9b568ca7d01a2d5_1280.jpg",
            "https://pixabay.com/get/g6b2fb163228641fe1b7b8f17f6a619d5ddc3eb21ddc7a4dddc6bcd3766e4b60249aaafb12b063ea0f26aad896a9f4d86_1280.jpg",
            "https://pixabay.com/get/g2ff5578be6b406d0f2b0df1a8276d7a37ecee7ff9ee77bf0142661687b17f18fa71ec5862ae2f9fa8d374fcf6cd5e693_1280.jpg",
            "https://pixabay.com/get/g1feab34d004abb258549200e3dece06114bdb7d770dd8728782ef5c7084ceae324a006b3483ec8447366e93cde34613d_1280.jpg",
            "https://pixabay.com/get/g152a971db8e25bfdcdf87b4992ded20df5a6c72fb860559262db71601c70c916688a7112e67cc8c4adc89f9328ea6447_1280.jpg",
            "https://pixabay.com/get/g16dd05dff432ed1635c00731787219e72d9812651da0979cedecbe2fff484fae7459b5a6db28b763b6f008ff1c40efb3_1280.jpg",
            "https://pixabay.com/get/g355c4335f639853e5653d91dc8476a4f06208191664bd2778184560e6e2783695e36255a7455245255ffe8715e90b41b_1280.jpg",
            "https://pixabay.com/get/g5da81a5bfc81740c354affaa541933d8da324348d5bf8608ce01157d79224890ef16d634b6629534ce65130712a7ca46_1280.jpg",
            "https://pixabay.com/get/gf8e10708a9460fa600b60bc5f95e345c856c593549f9ebbd9bb202e2ded174e0588e169233f24c44767e6d1697bb9695_1280.jpg",
            "https://pixabay.com/get/g77b591de32cef10e399c7d4c9532de5a54c155cb86d4a52e7daf282e2a1c782e9e2faf9c21a369312fecaf3665e2fd1c_1280.jpg",
            "https://pixabay.com/get/gbd9d49caf87c140dd13495fef03318d4be9175c008970a6efa3ca4a912948fe4c7f6d92ea83c7a03926046ecc4c190c3_1280.jpg",
            "https://pixabay.com/get/g05cf818fdc1ae3f868903b168718e745f8cf9e2a413164566f222ba6e7d2942f843ed0f5128bc17e6e9529f61f2e145c_1280.jpg",
        )

        suspend fun requestVerificationCode() {
            delay(2000L)
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