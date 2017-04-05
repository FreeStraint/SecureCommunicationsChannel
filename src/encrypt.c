#include <jni.h>
#include <jni_md.h>
#include <stdio.h>
#include <stdlib.h>
#include "Encrypt.h"

void encrypt (long *v, long *k){
/* TEA encryption algorithm */
unsigned long y = v[0], z=v[1], sum = 0;
unsigned long delta = 0x9e3779b9, n=32;

	while (n-- > 0){
		sum += delta;
		y += (z<<4) + k[0] ^ z + sum ^ (z>>5) + k[1];
		z += (y<<4) + k[2] ^ y + sum ^ (y>>5) + k[3];
	}

	v[0] = y;
	v[1] = z;
}



void decrypt (long *v, long *k){
/* TEA decryption routine */
unsigned long n=32, sum, y=v[0], z=v[1];
unsigned long delta=0x9e3779b9l;

	sum = delta<<5;
	while (n-- > 0){
		z -= (y<<4) + k[2] ^ y + sum ^ (y>>5) + k[3];
		y -= (z<<4) + k[0] ^ z + sum ^ (z>>5) + k[1];
		sum -= delta;
	}
	v[0] = y;
	v[1] = z;
}

JNIEXPORT void JNICALL Java_Encrypt_encrypt
  (JNIEnv *env, jobject thisObj, jbyteArray value, jbyteArray key){

  	printf("encrypt\n");
  	jbyte *v = (*env)->GetByteArrayElements(env, value, 0);
  	jbyte *k = (*env)->GetByteArrayElements(env, key, 0);

  	jsize vSize = (*env)->GetArrayLength(env, value);

  	long* ve = (long*) v
  	long* ke = (long*) k
  	int i = 0;
  	for(i = 0; i<vSize; i+=2){
  		encrypt(ve, ke);
  	}
  }


JNIEXPORT void JNICALL Java_Encrypt_decrypt
  (JNIEnv *env, jobject thisObj, jbyteArray value, jbyteArray key){

  	// jbyte *v = (*env)->GetByteArrayElements(env, value, 0);
  	// jdouble *k = (*env)->GetDoubleArrayElements(env, key, 0);

  	// jsize vSize = (*env)->GetArrayLength(env, value);
  	// int i = 0;
  	// for(i = 0; i<vSize; i+=2){
  	// 	decrypt(v, k)
  	// }
  	printf("decrypt\n");
  }

#include <stdlib.h>
#include <stdio.h>



int main(int argc, char const *argv[])
{
  
  char* v = "AD DE E2 DB B3 E2 DB B3";
  char* k = "3A DA 75 21 DB E2 DB B3 11 B4 49 01 A5 C6 EA D4";
  printf("Value: %s\n", v);
  printf("Key: %s\n", k);


  return 0;
}


