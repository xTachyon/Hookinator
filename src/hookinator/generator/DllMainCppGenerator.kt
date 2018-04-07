package hookinator.generator

class DllMainCppGenerator(val functions: List<HookedFunction>, folder: String) {
  private val writer = CppWriter("$folder/dllmain.cpp")

  fun generate() {
    writeIncludes()

    writeInitHook()
    writer.writeNewLine()
    writeDeinitHook()
    writer.writeNewLine()

    writeDllMain()
  }

  fun finish() = writer.finish()

  private fun writeDllMain() {
    writer.write("BOOL APIENTRY DllMain(HMODULE, DWORD reason, LPVOID) {\n" +
        "  switch (reason) {\n" +
        "  case DLL_PROCESS_ATTACH:\n" +
        "    alloc_console();\n" +
        "    init_hook();\n" +
        "    break;\n" +
        "\n" +
        "  case DLL_PROCESS_DETACH:\n" +
        "    deinit_hook();\n" +
        "    break;\n" +
        "  }\n" +
        "  return TRUE;\n" +
        "}\n")
  }

  private fun writeIncludes() {
    writer.writeInclude("detours.h")
    writer.writeInclude("hooks.hpp", false)
    writer.writeNewLine()
  }

  private fun writeInitHook() {
    writer.write("void init_hook() {\n" +
        generateDetourThingy("DetourAttach") +
        "}\n")
  }

  private fun writeDeinitHook() {
    writer.write("void deinit_hook() {\n" +
        generateDetourThingy("DetourDetach") +
        "}\n")
  }

  private fun generateDetourThingy(detourFunction: String): String {
    return "DetourTransactionBegin();\n" +
        "DetourUpdateThread(GetCurrentThread());\n" +
        "${generateDetourHook(detourFunction)}\n" +
        "DetourTransactionCommit();\n"
  }

  private fun generateDetourHook(detourFunction: String): String {
    val string = StringBuilder()
    for (i in functions) {
      string.append("$detourFunction(&(PVOID &) real_${i.name}, ${i.hookedName});\n")
    }

    return string.toString()
  }
}