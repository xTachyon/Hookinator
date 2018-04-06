#include <Winsock2.h>
#include <Windows.h>
#include <string>

SOCKET WSAAPI hooked_socket(int af, int type, int protocol) {
log("Entering function hooked_socket");
log("Leaving function hooked_socket");
}

int WSAAPI hooked_recv(SOCKET s, char * buf, int len, int flags) {
log("Entering function hooked_recv");
log("Leaving function hooked_recv");
}

int WSAAPI hooked_WSARecv(SOCKET s, LPWSABUF lpBuffers, DWORD dwBufferCount, LPDWORD lpNumberOfBytesRecvd, LPDWORD lpFlags, LPWSAOVERLAPPED lpOverlapped, _In_opt_ LPWSAOVERLAPPED_COMPLETION_ROUTINE lpCompletionRoutine) {
log("Entering function hooked_WSARecv");
log("Leaving function hooked_WSARecv");
}

