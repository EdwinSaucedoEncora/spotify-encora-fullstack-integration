import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

// This function can be marked `async` if using `await` inside
export async function middleware(request: NextRequest) {
  const path = request.nextUrl.pathname;
  if (path.startsWith("/callback")) {
    const page = request.nextUrl.searchParams.get("redirect");
    const url = new URL("/callback", request.url);
    if (page) {
      url.searchParams.set("page", page);
      return NextResponse.redirect(url);
    }
  }
  return NextResponse.next();
}

export const config = {
  matcher: ["/callback$", "/home", "/album", "/artist"],
};
