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

JNIEXPORT jbyteArray JNICALL Java_Encrypt_encrypt
  (JNIEnv *env, jobject thisObj, jbyteArray value, jlongArray key){

  	printf("encrypt\n");
  	jbyte *v = (*env)->GetByteArrayElements(env, value, 0);
  	jlong *k = (*env)->GetLongArrayElements(env, key, 0);
    jboolean is_copy_value;
    jboolean is_copy_key;
  	jsize vSize = (*env)->GetArrayLength(env, value);

    long *a = (long *) v;
    //long *b = (long *) k;
	while((jbyte *) a < v + vSize){
		encrypt(a, (long *) k);
		a += 2;
	}

	jbyteArray res = (*env)->NewByteArray(env, vSize);
	(*env)->SetByteArrayRegion(env, res, 0, vSize, v);
	//return res;
	(*env)->ReleaseByteArrayElements(env, value, v, is_copy_value);
	(*env)->ReleaseLongArrayElements(env, key, k, is_copy_key);
	return res;
  }


JNIEXPORT jbyteArray JNICALL Java_Encrypt_decrypt
(JNIEnv *env, jobject thisObj, jbyteArray value, jlongArray key){

    jboolean is_copy_key;
    jboolean is_copy_value;
  	jbyte *v = (*env)->GetByteArrayElements(env, value, 0);
  	jlong *k = (*env)->GetLongArrayElements(env, key, 0);

    long *a = (long *) v;
    //long *b = (long *) k;

  	jsize vSize = (*env)->GetArrayLength(env, value);
	
    while((jbyte *) a < v + vSize){
		decrypt(a, (long *) k);
		a += 2;
	}
    //  	int i = 0;
    //  	for(i = 0; i<vSize; i+=2){
    //  		decrypt(a, b);
    //  	}
	
	jbyteArray res = (*env)->NewByteArray(env, vSize);
	(*env)->SetByteArrayRegion(env, res, 0, vSize, v);
	printf("Decrypt\n");	
	
   	(*env)->ReleaseByteArrayElements(env, value, v, is_copy_value);
   	(*env)->ReleaseLongArrayElements(env, key, k, is_copy_key);
  	return res;
    
}

int main(int argc, char const *argv[])
{
	long key[4];
	long v[] = {0xFFFFFFFF, 0xFFFFFFFF};
	printf("Original values\n");
	printf("[%lX %lX]\n", v[0], v[1]);

	encrypt(v, key);
	printf("[%lX %lX]\n", v[0], v[1]);

	decrypt(v,key);
	printf("[%lX %lX]\n", v[0], v[1]);

    printf("Do it tomorrow\n");
    /* code */
    return 0;
}


