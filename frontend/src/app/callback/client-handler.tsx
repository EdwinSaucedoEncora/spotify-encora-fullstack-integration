"use client";

import { setToken } from "@/lib/authSlice";
import { useAppDispatch, useAppStore } from "@/lib/hooks";
import { useRouter, useSearchParams } from "next/navigation";
import { useEffect, Suspense } from "react";

function Callback() {
  const router = useRouter();
  const searchParams = useSearchParams();
  const store = useAppStore();
  const dispatch = useAppDispatch();
  useEffect(() => {
    const {
      page = "/home",
      refresh_token,
      access_token,
      state,
      expires_in = 0,
    } = Object.fromEntries(searchParams.entries());

    const hasProps = [refresh_token, access_token, state, expires_in].every(
      (val) => Boolean(val)
    );

    const session = localStorage.getItem("session");
    const today = new Date();
    dispatch(setToken(access_token));

    if (session?.length) {
      const parsedSession = JSON.parse(session);
      const { expires_at } = parsedSession;

      if (new Date(expires_at) <= today) {
        console.log("Session Updated");
      } else {
        console.log("Session initiated");
      }

      router.push(page);
    } else if (!hasProps) {
      router.push("/login");
    } else {
      const totalMilliseconds = today.getTime() + Number(expires_in) * 1000;
      const expires_at = new Date(totalMilliseconds);
      localStorage.setItem(
        "session",
        JSON.stringify({
          refresh_token,
          access_token,
          state,
          expires_at,
        })
      );
      router.push(page);
    }
  }, [searchParams, router]);

  return <></>;
}

export default function ClientHandler() {
  return (
    <Suspense fallback={<div>Loading...</div>}>
      <Callback />
    </Suspense>
  );
}
